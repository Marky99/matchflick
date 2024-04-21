package com.marky.matchflick.api;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class JwtClaims {

  public static final String ROLE_CLAIM = "scope";
  public static final String USERNAME = "username";
}

