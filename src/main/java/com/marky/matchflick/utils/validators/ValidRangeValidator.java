package com.marky.matchflick.utils.validators;

import com.marky.matchflick.annotation.ValidRange;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ValidRangeValidator implements ConstraintValidator<ValidRange, Float> {

  private float min;
  private float max;

  @Override
  public void initialize(ValidRange constraintAnnotation) {
    min = constraintAnnotation.min();
    max = constraintAnnotation.max();
  }

  @Override
  public boolean isValid(Float value, ConstraintValidatorContext context) {
    return value == null || (value >= min && value <= max);
  }
}
