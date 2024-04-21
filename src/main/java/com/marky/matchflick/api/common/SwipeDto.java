package com.marky.matchflick.api.common;

import jakarta.validation.constraints.NotNull;
import java.util.UUID;
import lombok.Data;

@Data
public class SwipeDto {

  @NotNull
  private UUID baseEntityId;
  private boolean decision;
}
