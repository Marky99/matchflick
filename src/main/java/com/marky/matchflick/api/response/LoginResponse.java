package com.marky.matchflick.api.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginResponse {

  private String accessToken;
  private String refreshToken;
}