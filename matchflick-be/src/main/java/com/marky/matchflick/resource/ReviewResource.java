package com.marky.matchflick.resource;

import com.marky.matchflick.annotation.CustomApiResponse;
import com.marky.matchflick.api.common.MovieSimpleDto;
import com.marky.matchflick.api.common.ReviewDto;
import com.marky.matchflick.api.response.ReviewResponse;
import com.marky.matchflick.service.ReviewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.converters.models.PageableAsQueryParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Reviews")
@RestController
@RequiredArgsConstructor
@RequestMapping("/reviews")
@SecurityRequirement(name = "Authorization")
public class ReviewResource {

  private final ReviewService reviewService;

  @PostMapping("/last")
  @Operation(summary = "${api.review.last.title}", description = "${api.review.last.detail}")
  @CustomApiResponse(description = "${api.review.last.start}")
  public void postReviewLastSelection(@Valid @RequestBody ReviewDto reviewDto,
      @RequestParam UUID baseEntityId) {
    reviewService.postReviewLastSelection(reviewDto, baseEntityId);
  }

  @GetMapping("/last")
  @Operation(summary = "${api.review.last.get.title}", description = "${api.review.last.get.detail}")
  @CustomApiResponse(description = "${api.review.last.get.start}")
  public ResponseEntity<MovieSimpleDto> getReviewLastSelection() {
    return ResponseEntity.of(reviewService.getReviewLastSelection());
  }

  @GetMapping
  @Operation(summary = "${api.review.get.title}", description = "${api.review.get.detail}")
  @CustomApiResponse(description = "${api.review.get.start}")
  @PageableAsQueryParam
  public Page<ReviewResponse> getMyReviews(@Parameter(hidden = true) Pageable pageable) {
    return reviewService.getReviews(pageable);
  }

  @GetMapping("/{id}")
  @Operation(summary = "${api.review.get.id.title}", description = "${api.review.get.id.detail}")
  @CustomApiResponse(description = "${api.review.get.id.start}")
  public ResponseEntity<ReviewDto> getReview(@NotNull @PathVariable UUID id) {
    return ResponseEntity.of(reviewService.getReview(id));
  }
}
