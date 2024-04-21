package com.marky.matchflick.service.impl;

import com.marky.matchflick.api.common.MovieSimpleDto;
import com.marky.matchflick.api.common.ReviewDto;
import com.marky.matchflick.api.response.ReviewResponse;
import com.marky.matchflick.domain.Profile;
import com.marky.matchflick.domain.features.BaseEntity;
import com.marky.matchflick.exception.EntityNotFoundException;
import com.marky.matchflick.mapper.MovieMapper;
import com.marky.matchflick.mapper.ReviewMapper;
import com.marky.matchflick.repository.MovieRepository;
import com.marky.matchflick.repository.ProfileMatchRepository;
import com.marky.matchflick.repository.ProfileRepository;
import com.marky.matchflick.repository.ReviewRepository;
import com.marky.matchflick.security.PrincipalHolder;
import com.marky.matchflick.service.ReviewService;
import jakarta.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

  private final ReviewRepository reviewRepository;
  private final ReviewMapper reviewMapper;
  private final ProfileMatchRepository profileMatchRepository;
  private final PrincipalHolder principalHolder;
  private final MovieRepository movieRepository;
  private final MovieMapper movieMapper;
  private final ProfileRepository profileRepository;

  @Override
  public void postReviewLastSelection(@NonNull ReviewDto reviewDto, @NonNull UUID baseEntityId) {
    final var baseEntity = movieRepository.findById(baseEntityId)
        .orElseThrow(() -> new EntityNotFoundException(
            BaseEntity.class));
    final var profile = profileRepository.findById(principalHolder.getUserId())
        .orElseThrow(() -> new EntityNotFoundException(
            Profile.class));
    reviewRepository.saveAndFlush(reviewMapper.toEntity(reviewDto, baseEntity, profile));
  }

  @Override
  public Optional<MovieSimpleDto> getReviewLastSelection() {
    final var lastMatch = profileMatchRepository.findLastMatch(principalHolder.getUserId())
        .orElseThrow();
    final var hasReview = reviewRepository.hasReview(lastMatch, principalHolder.getUserId());
    return hasReview ? Optional.empty()
        : movieRepository.findById(lastMatch).map(movieMapper::toSimpleDto);
  }

  @Override
  public Page<ReviewResponse> getReviews(Pageable pageable) {
    return reviewRepository.findByProfileId(principalHolder.getUserId(), pageable)
        .map(reviewMapper::toResponse);
  }

  @Override
  public Optional<ReviewDto> getReview(@NonNull UUID id) {
    return reviewRepository.findById(id).map(reviewMapper::toResponse);
  }
}
