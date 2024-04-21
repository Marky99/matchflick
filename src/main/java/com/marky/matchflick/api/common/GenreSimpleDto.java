package com.marky.matchflick.api.common;

import java.util.UUID;
import lombok.Data;

@Data
public class GenreSimpleDto {

  private UUID id;
  private String name;
  private String description;
}
