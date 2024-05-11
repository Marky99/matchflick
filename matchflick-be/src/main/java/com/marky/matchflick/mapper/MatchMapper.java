package com.marky.matchflick.mapper;

import com.marky.matchflick.api.common.MatchDto;
import com.marky.matchflick.domain.Match;
import com.marky.matchflick.domain.features.BaseEntity;
import java.util.UUID;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface MatchMapper {

  @Mapping(target = "id", ignore = true)
  Match toEntity(UUID sessionId, boolean decision, BaseEntity baseEntity);

  MatchDto toDto(String baseEntityName, String hostUsername, String friend);
}
