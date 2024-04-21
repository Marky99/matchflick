package com.marky.matchflick.api.common;

import com.marky.matchflick.annotation.ValidRange;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ReviewDto {

  @NotNull
  private String title;

  @NotNull
  @ValidRange
  private Float score;

  @NotNull
  private String description;
}
