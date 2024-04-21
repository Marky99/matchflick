package com.marky.matchflick.security.impl;

import com.marky.matchflick.security.PrincipalHolder;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class PrincipalHolderImpl implements PrincipalHolder {

  @Override
  public Authentication getAuthentication() {
    return SecurityContextHolder.getContext().getAuthentication();
  }

  @Override
  public UUID getUserId() {
    return getClaim("sub").map(UUID::fromString).orElse(null);
  }

  @Override
  public String getUserIdForLogging() {
    return getClaim("sub").orElse(null);
  }

  @Override
  public Jwt getPrincipal() {
    final var auth = getAuthentication();

    if (auth != null && auth.getPrincipal() instanceof Jwt token) {
      return token;
    }

    return null;
  }

  @NonNull
  @Override
  public <T> Optional<T> getClaim(@NonNull String claim, @NonNull Class<T> clazz) {
    final var principal = getPrincipal();

    if (principal == null) {
      return Optional.empty();
    }

    return Optional.ofNullable(principal.getClaim(claim));
  }
}
