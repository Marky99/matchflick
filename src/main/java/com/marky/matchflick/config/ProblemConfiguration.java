package com.marky.matchflick.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.core.OAuth2ErrorCodes;
import org.springframework.security.oauth2.jwt.JwtValidationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.NativeWebRequest;
import org.zalando.problem.Problem;
import org.zalando.problem.Status;
import org.zalando.problem.jackson.ProblemModule;
import org.zalando.problem.spring.common.AdviceTrait;
import org.zalando.problem.spring.web.advice.ProblemHandling;
import org.zalando.problem.spring.web.advice.security.SecurityAdviceTrait;
import org.zalando.problem.violations.ConstraintViolationProblemModule;

@Configuration
public class ProblemConfiguration {

  @ControllerAdvice
  public static class SecurityExceptionHandling implements ProblemHandling, SecurityAdviceTrait {

    @ExceptionHandler(JwtValidationException.class)
    public ResponseEntity<Problem> handleJwtException(final JwtValidationException ex,
        final NativeWebRequest request) {
      if (!ex.getErrors().isEmpty() && ex.getErrors().iterator().next().getErrorCode()
          .equals(OAuth2ErrorCodes.INVALID_TOKEN)) {
        return create(Status.UNAUTHORIZED, ex, request);
      }

      return create(Status.INTERNAL_SERVER_ERROR, ex, request);
    }
  }

  @Bean
  public AdviceTrait securityExceptionHandling() {
    return new SecurityExceptionHandling();
  }

  @Bean
  @ConditionalOnMissingBean(ProblemModule.class)
  public ProblemModule problemModuleOverride() {
    return new ProblemModule().withStackTraces(false);
  }

  @Bean
  @ConditionalOnMissingBean(ConstraintViolationProblemModule.class)
  public ConstraintViolationProblemModule constraintViolationProblemModule() {
    return new ConstraintViolationProblemModule();
  }

}