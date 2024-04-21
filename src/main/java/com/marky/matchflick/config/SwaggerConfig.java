package com.marky.matchflick.config;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import java.util.List;
import java.util.Properties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.info.BuildProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for Swagger documentation settings. This class defines configurations for
 * generating Swagger documentation using the OpenAPI specification.
 *
 * <p>The configuration includes the following features:
 * <ul>
 * <li> Definition of a bearer authentication scheme for API endpoints.
 * <li> Automatic detection of build properties using {@link BuildProperties}.
 * <li> Configuration of API information such as title and version.
 * <li> Configuration of a local server for testing purposes.
 * </ul>
 *
 * <p>Note: The proxyBeanMethods attribute is set to false in order to ensure that the @Bean methods
 * are not proxied, preserving their direct invocation. This might be important when dealing
 * with configurations involving bean creation and customization.
 *
 * @author Marek Valentiny
 */
@Configuration(proxyBeanMethods = false)
@SecurityScheme(
    name = "Authorization",
    scheme = "bearer",
    bearerFormat = "JWT",
    type = SecuritySchemeType.HTTP,
    in = SecuritySchemeIn.HEADER
)
public class SwaggerConfig {

  /**
   * Creates a {@link BuildProperties} bean when no custom bean is provided. This bean provides
   * build-related properties, such as artifact name and version.
   *
   * @return A configured {@link BuildProperties} instance.
   */
  @Bean
  @ConditionalOnMissingBean(BuildProperties.class)
  public BuildProperties buildPropertiesNoInstall() {
    final var props = new Properties();
    props.setProperty("artifact", "matchflick");
    props.setProperty("version", "local");

    return new BuildProperties(props);
  }

  @Configuration
  public static class OpenApiConfigs {

    /**
     * Creates an {@link OpenAPI} instance for generating API documentation.
     *
     * @return A configured {@link OpenAPI} instance representing API documentation.
     */
    @Bean
    public OpenAPI customOpenAPI(
        @Value("${openapi.service.title}") String serviceTitle,
        @Value("${openapi.service.version}") String serviceVersion,
        @Value("${openapi.service.url}") String url) {
      return new OpenAPI()
          .servers(List.of(new Server().url(url)))
          .info(new Info().title(serviceTitle).version(serviceVersion));
    }
  }
}