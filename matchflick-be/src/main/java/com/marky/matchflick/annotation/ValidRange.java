package com.marky.matchflick.annotation;

import com.marky.matchflick.utils.validators.ValidRangeValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Constraint(validatedBy = ValidRangeValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidRange {

  String message() default "Range must be between {min} and {max}";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};

  float min() default 1;

  float max() default 10;
}
