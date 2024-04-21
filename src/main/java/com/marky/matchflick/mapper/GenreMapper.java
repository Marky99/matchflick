package com.marky.matchflick.mapper;

import com.marky.matchflick.api.common.GenreDto;
import com.marky.matchflick.api.common.GenreSimpleDto;
import com.marky.matchflick.domain.Genre;
import org.mapstruct.Mapper;

@Mapper
public interface GenreMapper {

  GenreDto toDto(String name, Float score, Integer decisionCount);

  GenreSimpleDto toSimpleDto(Genre genre);
}
