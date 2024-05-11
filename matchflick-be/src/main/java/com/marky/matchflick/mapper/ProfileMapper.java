package com.marky.matchflick.mapper;

import com.marky.matchflick.api.common.UserDto;
import com.marky.matchflick.domain.Profile;
import org.mapstruct.Mapper;

@Mapper
public interface ProfileMapper {

  UserDto map(Profile user);
}
