package com.marky.matchflick.exception.problem.error;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class ErrorResponse {

  @Schema(nullable = true)
  private String type;
  private String title;
  @Schema(nullable = true)
  private String detail;
  @Schema(nullable = true)
  private String code;
  private int status;
}