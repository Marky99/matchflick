package com.marky.matchflick.service.impl;

import com.marky.matchflick.api.common.FriendDto;
import com.marky.matchflick.api.common.GenreDto;
import com.marky.matchflick.api.common.GenreSimpleDto;
import com.marky.matchflick.api.common.MatchDto;
import com.marky.matchflick.api.common.SessionDto;
import com.marky.matchflick.api.common.UserDto;
import com.marky.matchflick.api.request.AuthRequest;
import com.marky.matchflick.domain.Profile;
import com.marky.matchflick.exception.EntityNotFoundException;
import com.marky.matchflick.exception.UserAlreadyExistException;
import com.marky.matchflick.mapper.GenreMapper;
import com.marky.matchflick.mapper.MatchMapper;
import com.marky.matchflick.mapper.ProfileMapper;
import com.marky.matchflick.repository.GenreRepository;
import com.marky.matchflick.repository.MatchRepository;
import com.marky.matchflick.repository.ProfileGenreRepository;
import com.marky.matchflick.repository.ProfileMatchRepository;
import com.marky.matchflick.repository.ProfileRepository;
import com.marky.matchflick.security.PrincipalHolder;
import com.marky.matchflick.service.ProfileService;
import com.marky.matchflick.utils.StaticObjectFactory;
import jakarta.transaction.Transactional;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {

  private final ProfileRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final ProfileMapper userMapper;
  private final PrincipalHolder principalHolder;
  private final GenreRepository genreRepository;
  private final ProfileGenreRepository profileGenreRepository;
  private final StaticObjectFactory staticObjectFactory;
  private final ProfileMatchRepository profileMatchRepository;
  private final MatchRepository matchRepository;
  private final MatchMapper matchMapper;
  private final GenreMapper genreMapper;

  @NonNull
  @Override
  public UUID registerNewUser(@NonNull AuthRequest request) {
    if (userRepository.existsByUsername(request.getUsername())) {
      throw new UserAlreadyExistException(request.getUsername());
    }
    return userRepository.save(
        new Profile(request.getUsername(), passwordEncoder.encode(request.getPassword()))).getId();
  }

  @NonNull
  @Override
  @ReadOnlyProperty
  public UserDto findById(@NonNull UUID userId) {
    final var profile = userRepository.findById(userId)
        .orElseThrow(() -> new EntityNotFoundException(
            Profile.class));
    return userMapper.map(profile);
  }

  @NonNull
  @Override
  public void assignGenres(List<UUID> newGenreIds) {
    final var genres = genreRepository.findAllIds();
    final var userId = principalHolder.getUserId();

    profileGenreRepository.assignNewGenres(userId, genres,
        (newGenreIds == null || newGenreIds.isEmpty()) ? Collections.emptyList() : newGenreIds);
  }

  @Override
  public void startSession(@NonNull SessionDto sessionDto) {
    final var sessionId = staticObjectFactory.getRandomId();
    setSession(sessionDto.getUsername(), sessionId, principalHolder.getUserId());
  }

  @Override
  public void endSession(@NonNull SessionDto sessionDto) {
    setSession(sessionDto.getUsername(), null, principalHolder.getUserId());
  }

  @Override
  public Optional<UserDto> hasSessionStarted() {
    return userRepository.findOtherSession(principalHolder.getUserId()).map(userMapper::map);
  }

  // TODO didnt have time to implement this
  @Override
  public Page<FriendDto> getFriends(Pageable pageable) {
    return null;
  }

  @Override
  public Page<MatchDto> getMatches(Pageable pageable) {
    final var result = profileMatchRepository.findMyMatches(principalHolder.getUserId());
    return matchRepository.findAllByIdIn(result, pageable).map(m -> {
      final var hostUsername = m.getProfileMatches().stream()
          .filter(p -> p.getProfile().getId().equals(principalHolder.getUserId())).findFirst()
          .orElseThrow()
          .getProfile().getUsername();
      final var friend = m.getProfileMatches().stream()
          .filter(p -> !p.getProfile().getId().equals(principalHolder.getUserId())).findFirst()
          .orElseThrow()
          .getProfile().getUsername();
      return matchMapper.toDto(m.getBaseEntity().getName(), hostUsername, friend);
    });
  }

  @Override
  public Page<GenreDto> getGenres(Pageable pageable) {
    return profileGenreRepository.findGenres(pageable, principalHolder.getUserId()).map(p -> {
      final var decisionCount = profileMatchRepository.countMatchGenreDecision(
          principalHolder.getUserId(),
          p.getGenre().getId());
      return genreMapper.toDto(p.getGenre().getName(), p.getScore(), decisionCount);
    });
  }

  @Override
  public Page<GenreSimpleDto> getAllGenres(Pageable pageable) {
    return genreRepository.findAll(pageable).map(genreMapper::toSimpleDto);
  }

  private void setSession(String username, UUID sessionId, UUID hostProfileId) {
    userRepository.updateProfileSessionId(username, sessionId);
    userRepository.updateProfileSessionId(hostProfileId, sessionId);
  }
}
