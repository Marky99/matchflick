package com.marky.matchflick.mapper;

import com.marky.matchflick.api.common.MovieDto;
import com.marky.matchflick.api.common.MovieSimpleDto;
import com.marky.matchflick.domain.features.Movie;
import org.mapstruct.Mapper;

@Mapper
public interface MovieMapper {

  MovieDto toDto(Movie movie);

  MovieSimpleDto toSimpleDto(Movie movie);
}
