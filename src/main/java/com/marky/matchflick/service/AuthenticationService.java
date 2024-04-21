package com.marky.matchflick.service;

import com.marky.matchflick.api.request.AuthRequest;
import com.marky.matchflick.api.request.RefreshTokenRequest;
import com.marky.matchflick.api.response.LoginResponse;
import org.springframework.lang.NonNull;

public interface AuthenticationService {

  @NonNull
  LoginResponse performLogin(@NonNull AuthRequest request);

  @NonNull
  LoginResponse refresh(@NonNull RefreshTokenRequest request);

  @NonNull
  LoginResponse registerUser(@NonNull AuthRequest request);
}
