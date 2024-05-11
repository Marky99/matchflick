package com.marky.matchflick.api.response;

import com.marky.matchflick.api.common.ReviewDto;
import java.util.UUID;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ReviewResponse extends ReviewDto {

  private UUID id;
}
