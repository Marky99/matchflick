package com.marky.matchflick.resource;

import com.marky.matchflick.annotation.CustomApiResponse;
import com.marky.matchflick.api.common.FriendDto;
import com.marky.matchflick.api.common.GenreDto;
import com.marky.matchflick.api.common.GenreSimpleDto;
import com.marky.matchflick.api.common.MatchDto;
import com.marky.matchflick.api.common.SessionDto;
import com.marky.matchflick.api.common.UserDto;
import com.marky.matchflick.service.ProfileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.converters.models.PageableAsQueryParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Profile")
@RestController
@RequiredArgsConstructor
@RequestMapping("/profiles")
@SecurityRequirement(name = "Authorization")
public class ProfileResource {

  private final ProfileService profileService;

  @PostMapping("/genres")
  @Operation(summary = "${api.profile.assign.title}", description = "${api.profile.assign.detail}")
  @CustomApiResponse(description = "${api.profile.assign.start}")
  public void assignGenres(@RequestBody(required = false) List<UUID> genreId) {
    profileService.assignGenres(genreId);
  }

  @PostMapping("/session")
  @Operation(summary = "${api.profile.session.title}", description = "${api.profile.session.detail}")
  @CustomApiResponse(description = "${api.profile.session.start}")
  public void startSession(@RequestBody SessionDto sessionDto) {
    profileService.startSession(sessionDto);
  }

  @GetMapping("/session")
  @Operation(summary = "${api.profile.session.get.title}", description = "${api.profile.session.get.detail}")
  @CustomApiResponse(description = "${api.profile.session.get.start}")
  public ResponseEntity<UserDto> hasSessionStarted() {
    return ResponseEntity.of(profileService.hasSessionStarted());
  }

  @PutMapping("/session")
  @Operation(summary = "${api.profile.session.put.title}", description = "${api.profile.session.put.detail}")
  @CustomApiResponse(description = "${api.profile.session.put.start}")
  public void endSession(SessionDto sessionDto) {
    profileService.endSession(sessionDto);
  }

  @GetMapping("/friends")
  @Operation(summary = "${api.profile.friends.get.title}", description = "${api.profile.friends.get.detail}")
  @CustomApiResponse(description = "${api.profile.friends.get.start}")
  @PageableAsQueryParam
  public Page<FriendDto> getFriends(@Parameter(hidden = true) Pageable pageable) {
    return profileService.getFriends(pageable);
  }

  @GetMapping("/matches")
  @Operation(summary = "${api.profile.matches.get.title}", description = "${api.profile.matches.get.detail}")
  @CustomApiResponse(description = "${api.profile.matches.get.start}")
  @PageableAsQueryParam
  public Page<MatchDto> getMatches(@Parameter(hidden = true) Pageable pageable) {
    return profileService.getMatches(pageable);
  }

  @GetMapping("/genres")
  @Operation(summary = "${api.profile.genre.get.title}", description = "${api.profile.genre.get.detail}")
  @CustomApiResponse(description = "${api.profile.genre.get.start}")
  @PageableAsQueryParam
  public Page<GenreDto> getGenres(@Parameter(hidden = true) Pageable pageable) {
    return profileService.getGenres(pageable);
  }

  @GetMapping("/genres/all")
  @Operation(summary = "${api.profile.genre.get.all.title}", description = "${api.profile.genre.get.all.detail}")
  @CustomApiResponse(description = "${api.profile.genre.get.all.start}")
  @PageableAsQueryParam
  public Page<GenreSimpleDto> getAllGenres(@Parameter(hidden = true) Pageable pageable) {
    return profileService.getAllGenres(pageable);
  }
}
