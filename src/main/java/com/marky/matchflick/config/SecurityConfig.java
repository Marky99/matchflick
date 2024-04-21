package com.marky.matchflick.config;

import com.marky.matchflick.service.impl.UserDetailServiceImpl;
import com.marky.matchflick.utils.StaticObjectFactory;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtTimestampValidator;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.oauth2.server.resource.web.BearerTokenAuthenticationEntryPoint;
import org.springframework.security.oauth2.server.resource.web.access.BearerTokenAccessDeniedHandler;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.zalando.problem.spring.web.advice.security.SecurityProblemSupport;

@Configuration
@EnableWebSecurity
@Import(SecurityProblemSupport.class)
@RequiredArgsConstructor
public class SecurityConfig {

  /**
   * Represents set of default public endpoints
   */
  private static final String[] SWAGGER_PUBLIC_ENDPOINTS = {
      "/swagger-ui.html",
      "/swagger-ui/**",
      "/v3/api-docs/**",
  };

  private static final List<String> ALLOWED_CORS_HEADERS = List.of("x-requested-with",
      "authorization", "origin",
      "content-type", "version",
      "content-disposition", "location");

  private final UserDetailServiceImpl userDetailsService;

  @Value("${jwt.public.key}")
  private RSAPublicKey key;

  @Value("${jwt.private.key}")
  private RSAPrivateKey priv;

  /**
   * Configures the security filter chain for the application.
   *
   * <p>This method sets up the security filter chain with specific security configurations,
   * including authorization rules, CSRF protection, CORS configuration, authentication mechanisms,
   * OAuth2 resource server settings, session management, and exception handling.
   *
   * @param http The {@link HttpSecurity} object to configure the security settings.
   * @return A {@link SecurityFilterChain} instance representing the configured security filter
   * chain.
   * @throws Exception If an error occurs while configuring the security settings.
   * @author Marek Valentiny
   */
  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
        .authorizeHttpRequests(authorize -> authorize
            .requestMatchers(HttpMethod.GET, SWAGGER_PUBLIC_ENDPOINTS).permitAll()
            .requestMatchers(HttpMethod.POST, "/auth/**").permitAll()
            .anyRequest().authenticated()
        )
        .csrf(AbstractHttpConfigurer::disable)
        .cors(cors -> cors.configurationSource(corsFilter()))
        .httpBasic(Customizer.withDefaults())
        .oauth2ResourceServer(oauth -> oauth.jwt(Customizer.withDefaults()))
        .sessionManagement(
            session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .exceptionHandling(exceptions -> exceptions
            .authenticationEntryPoint(new BearerTokenAuthenticationEntryPoint())
            .accessDeniedHandler(new BearerTokenAccessDeniedHandler())
        );
    return http.build();
  }

  @Bean
  public UserDetailsService users() {
    return new InMemoryUserDetailsManager(
        User.withUsername("user")
            // password: password
            .password("$2a$12$PQTC6BA5leiAGry3IOLrd.ylLZ0jOOhsMHsKo7A4hJBs5OsHwzUN2")
            .authorities("app")
            .build()
    );
  }

  @Bean
  public CorsConfigurationSource corsFilter() {
    final var cfg = new CorsConfiguration();
    cfg.addAllowedOrigin("*");
    cfg.addAllowedHeader("*");
    cfg.addAllowedMethod("*");
    cfg.setExposedHeaders(ALLOWED_CORS_HEADERS);

    final var source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", cfg);

    return source;
  }

  @Bean
  public JwtDecoder jwtDecoder(StaticObjectFactory staticObjectFactory) {
    final var result = NimbusJwtDecoder.withPublicKey(key).build();
    final var validator = new JwtTimestampValidator();
    validator.setClock(staticObjectFactory.getClock());

    result.setJwtValidator(validator);

    return result;
  }

  @Bean
  public JwtEncoder jwtEncoder() {
    final var jwk = new RSAKey.Builder(this.key).privateKey(this.priv).build();
    final var jwks = new ImmutableJWKSet<>(new JWKSet(jwk));
    return new NimbusJwtEncoder(jwks);
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder(12);
  }

  @Bean
  public AuthenticationManager authenticationManager(
      AuthenticationConfiguration authenticationConfiguration)
      throws Exception {
    return authenticationConfiguration.getAuthenticationManager();
  }

  @Bean
  public AuthenticationProvider authenticationProvider() {
    final var authProvider = new DaoAuthenticationProvider();
    authProvider.setUserDetailsService(userDetailsService);
    authProvider.setPasswordEncoder(passwordEncoder());
    return authProvider;
  }

  @Bean
  public JwtAuthenticationConverter jwtAuthenticationConverter() {
    final var grantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
    grantedAuthoritiesConverter.setAuthorityPrefix("");
    JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
    jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(grantedAuthoritiesConverter);
    return jwtAuthenticationConverter;
  }
}
