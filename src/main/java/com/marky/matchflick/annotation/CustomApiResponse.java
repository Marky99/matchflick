package com.marky.matchflick.annotation;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.core.annotation.AliasFor;

/**
 * Aggregated annotation to fill default response bodies for each method annotated with this
 * annotation
 * <p>
 * Uses {@link StandardErrorResponseBody} {@code value} and {@code message} are used to fill the
 * @ApiResponse annotation by its representative Aliases
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@StandardErrorResponseBody
@ApiResponse(
    responseCode = "200",
    description = "OK"
)
public @interface CustomApiResponse {

  @AliasFor(annotation = ApiResponse.class, attribute = "responseCode")
  String responseCode() default "200";

  @AliasFor(annotation = ApiResponse.class, attribute = "description")
  String description() default "OK";
}
