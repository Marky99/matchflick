package com.marky.matchflick.service.impl;

import com.marky.matchflick.domain.Profile;
import com.marky.matchflick.exception.EntityNotFoundException;
import com.marky.matchflick.repository.ProfileRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.lang.NonNull;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class UserDetailServiceImpl implements UserDetailsService {

  private final ProfileRepository userRepository;

  @Override
  @ReadOnlyProperty
  public UserDetails loadUserByUsername(@NonNull String username) throws UsernameNotFoundException {
    return userRepository.findByUsername(username)
        .map(user -> User.withUsername(username).password(user.getPassword()))
        .orElseThrow(() -> new EntityNotFoundException(Profile.class)).build();
  }
}
