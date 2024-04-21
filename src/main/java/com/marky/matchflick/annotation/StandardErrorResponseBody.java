package com.marky.matchflick.annotation;

import com.marky.matchflick.exception.EntityNotFoundException;
import com.marky.matchflick.exception.problem.error.ConstraintError;
import com.marky.matchflick.exception.problem.error.ErrorResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Aggregated annotation to fill default error response bodies for each method annotated with this
 * annotation
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@ApiResponse(
    responseCode = "400",
    description = "When any of the parameters is in malformed format",
    content = @Content(schema = @Schema(implementation = ConstraintError.class))
)
@ApiResponse(
    responseCode = "401",
    description = "When authorization header is missing",
    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
)
@ApiResponse(
    responseCode = "403",
    description = "When user is missing proper permissions",
    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
)
@ApiResponse(
    responseCode = "404",
    description = "When entity could not be found",
    content = @Content(schema = @Schema(implementation = EntityNotFoundException.class))
)
@ApiResponse(
    responseCode = "500",
    description = "When internal server error occurred",
    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
)
public @interface StandardErrorResponseBody {

}