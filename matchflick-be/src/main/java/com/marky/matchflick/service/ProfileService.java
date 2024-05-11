package com.marky.matchflick.service;

import com.marky.matchflick.api.common.FriendDto;
import com.marky.matchflick.api.common.GenreDto;
import com.marky.matchflick.api.common.GenreSimpleDto;
import com.marky.matchflick.api.common.MatchDto;
import com.marky.matchflick.api.common.SessionDto;
import com.marky.matchflick.api.common.UserDto;
import com.marky.matchflick.api.request.AuthRequest;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;

public interface ProfileService {

  UUID registerNewUser(@NonNull AuthRequest request);

  @NonNull
  UserDto findById(@NonNull UUID userId);

  void assignGenres(List<UUID> genreIds);

  void startSession(@NonNull SessionDto sessionDto);

  void endSession(@NonNull SessionDto sessionDto);

  Optional<UserDto> hasSessionStarted();

  Page<FriendDto> getFriends(Pageable pageable);

  Page<MatchDto> getMatches(Pageable pageable);

  Page<GenreDto> getGenres(Pageable pageable);

  Page<GenreSimpleDto> getAllGenres(Pageable pageable);
}

