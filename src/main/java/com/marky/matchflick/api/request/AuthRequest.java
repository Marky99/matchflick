package com.marky.matchflick.api.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class AuthRequest {

  @NotNull
  private String username;

  @Size(min = 6)
  @NotNull
  private String password;
}
