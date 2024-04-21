package com.marky.matchflick.api.common;

import java.util.UUID;
import lombok.Data;

@Data
public class MovieSimpleDto {

  private UUID id;
  private String name;
  private String description;

}
