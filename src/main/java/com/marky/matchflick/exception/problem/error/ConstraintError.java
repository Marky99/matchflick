package com.marky.matchflick.exception.problem.error;

import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.zalando.problem.violations.Violation;

@Data
@EqualsAndHashCode(callSuper = true)
public class ConstraintError extends ErrorResponse {

  private List<Violation> violations;
}
