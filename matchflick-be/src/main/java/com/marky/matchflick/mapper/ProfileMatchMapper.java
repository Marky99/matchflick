package com.marky.matchflick.mapper;

import com.marky.matchflick.domain.Match;
import com.marky.matchflick.domain.Profile;
import com.marky.matchflick.domain.ProfileMatch;
import java.time.LocalDateTime;
import java.util.UUID;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface ProfileMatchMapper {

  @Mapping(target = "profile", source = "profile")
  @Mapping(target = "match", source = "match")
  @Mapping(target = "createdAt", source = "createdAt")
  @Mapping(target = "sessionId", source = "sessionId")
  ProfileMatch toEntity(Profile profile, Match match, LocalDateTime createdAt, UUID sessionId);
}
