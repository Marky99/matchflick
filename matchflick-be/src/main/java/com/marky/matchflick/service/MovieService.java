package com.marky.matchflick.service;

import com.marky.matchflick.api.common.MovieDto;
import com.marky.matchflick.api.common.SwipeDto;
import java.util.Optional;
import org.springframework.lang.NonNull;

public interface MovieService {

  @NonNull
  Optional<MovieDto> getRandomMovie();

  void swipeOnMovie(@NonNull SwipeDto swipeDto);

  Optional<String> checkMatch();
}

