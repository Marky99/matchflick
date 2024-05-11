package com.marky.matchflick.service.impl;

import com.marky.matchflick.api.common.MovieDto;
import com.marky.matchflick.api.common.SwipeDto;
import com.marky.matchflick.domain.Genre;
import com.marky.matchflick.domain.MovieGenre;
import com.marky.matchflick.domain.Profile;
import com.marky.matchflick.domain.features.BaseEntity;
import com.marky.matchflick.exception.EntityNotFoundException;
import com.marky.matchflick.mapper.MatchMapper;
import com.marky.matchflick.mapper.MovieMapper;
import com.marky.matchflick.mapper.ProfileMatchMapper;
import com.marky.matchflick.repository.MatchRepository;
import com.marky.matchflick.repository.MovieGenreRepository;
import com.marky.matchflick.repository.MovieRepository;
import com.marky.matchflick.repository.ProfileGenreRepository;
import com.marky.matchflick.repository.ProfileMatchRepository;
import com.marky.matchflick.repository.ProfileRepository;
import com.marky.matchflick.security.PrincipalHolder;
import com.marky.matchflick.service.MovieService;
import com.marky.matchflick.utils.StaticObjectFactory;
import jakarta.transaction.Transactional;
import jakarta.transaction.Transactional.TxType;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class MovieServiceImpl implements MovieService {

  private final MovieRepository movieRepository;
  private final ProfileGenreRepository profileGenreRepository;
  private final PrincipalHolder principalHolder;
  private final MovieGenreRepository movieGenreRepository;
  private final MovieMapper movieMapper;
  private final ProfileRepository profileRepository;
  private final ProfileMatchMapper profileMatchMapper;
  private final StaticObjectFactory staticObjectFactory;
  private final ProfileMatchRepository profileMatchRepository;
  private final MatchRepository matchRepository;
  private final MatchMapper matchMapper;

  @Override
  @NonNull
  public Optional<MovieDto> getRandomMovie() {
    final var genreOrMovie = new Random().nextInt(2);
    if (genreOrMovie == 0) {
      return movieRepository.findRandomMovie().map(movieMapper::toDto);
    }
    final var genreId = profileGenreRepository.findRandomGenre(principalHolder.getUserId());
    return movieRepository.findById(movieGenreRepository.findRandomByGenreId(genreId))
        .map(movieMapper::toDto);
  }

  @Override
  @Transactional(TxType.REQUIRES_NEW)
  public void swipeOnMovie(@NonNull SwipeDto swipeDto) {
    final var movie = movieRepository.findById(swipeDto.getBaseEntityId())
        .orElseThrow(() -> new EntityNotFoundException(BaseEntity.class));

    final var profile = profileRepository.findById(principalHolder.getUserId())
        .orElseThrow(() -> new EntityNotFoundException(Profile.class));

    final var genreIds = movie.getMovieGenre().stream()
        .map(MovieGenre::getGenre)
        .map(Genre::getId)
        .collect(Collectors.toList());

    profileGenreRepository.updateScoresForGenres(genreIds, swipeDto.isDecision() ? 0.1f : -0.1f,
        profile.getId());

    final var match = matchRepository.findBySessionIdAndBaseEntity(movie.getId(),
            profile.getCurrentSessionId())
        .orElseGet(() -> matchMapper.toEntity(profile.getCurrentSessionId(), swipeDto.isDecision(),
            movie));
    match.setDecision(swipeDto.isDecision());
    matchRepository.saveAndFlush(match);

    profileMatchRepository.save(
        profileMatchMapper.toEntity(profile, match, staticObjectFactory.nowLocalDateTime(),
            profile.getCurrentSessionId()));
  }

  @Override
  public Optional<String> checkMatch() {
    return profileMatchRepository.existMatch(principalHolder.getUserId());
  }
}