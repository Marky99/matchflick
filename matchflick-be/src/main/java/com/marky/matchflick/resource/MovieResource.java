package com.marky.matchflick.resource;

import com.marky.matchflick.annotation.CustomApiResponse;
import com.marky.matchflick.api.common.MovieDto;
import com.marky.matchflick.api.common.SwipeDto;
import com.marky.matchflick.service.MovieService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Movies")
@RestController
@RequiredArgsConstructor
@RequestMapping("/movies")
@SecurityRequirement(name = "Authorization")
public class MovieResource {

  private final MovieService movieService;

  @GetMapping("/session")
  @Operation(summary = "${api.movie.get.title}", description = "${api.movie.get.detail}")
  @CustomApiResponse(description = "${api.movie.get.start}")
  public ResponseEntity<MovieDto> getRandomMovie() {
    return ResponseEntity.of(movieService.getRandomMovie());
  }

  @GetMapping("/session/match")
  @Operation(summary = "${api.movie.match.title}", description = "${api.movie.match.detail}")
  @CustomApiResponse(description = "${api.movie.match.start}")
  public ResponseEntity<String> checkMatch() {
    return ResponseEntity.of(movieService.checkMatch());
  }

  @PostMapping("/session")
  @Operation(summary = "${api.movie.post.title}", description = "${api.movie.post.detail}")
  @CustomApiResponse(description = "${api.movie.post.start}")
  public void swipeOnMovie(@RequestBody @Valid SwipeDto swipeDto) {
    movieService.swipeOnMovie(swipeDto);
  }
}
