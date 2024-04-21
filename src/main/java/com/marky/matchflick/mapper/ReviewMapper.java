package com.marky.matchflick.mapper;

import com.marky.matchflick.api.common.ReviewDto;
import com.marky.matchflick.api.response.ReviewResponse;
import com.marky.matchflick.domain.Profile;
import com.marky.matchflick.domain.Review;
import com.marky.matchflick.domain.features.BaseEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface ReviewMapper {

  ReviewResponse toResponse(Review review);

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "description", source = "reviewDto.description")
  Review toEntity(ReviewDto reviewDto, BaseEntity baseEntity, Profile profile);
}
