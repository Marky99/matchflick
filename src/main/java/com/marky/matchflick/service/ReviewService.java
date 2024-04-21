package com.marky.matchflick.service;

import com.marky.matchflick.api.common.MovieSimpleDto;
import com.marky.matchflick.api.common.ReviewDto;
import com.marky.matchflick.api.response.ReviewResponse;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;

public interface ReviewService {

  void postReviewLastSelection(@NonNull ReviewDto reviewDto, @NonNull UUID baseEntityId);

  Optional<MovieSimpleDto> getReviewLastSelection();

  Page<ReviewResponse> getReviews(Pageable pageable);

  Optional<ReviewDto> getReview(@NonNull UUID id);
}
