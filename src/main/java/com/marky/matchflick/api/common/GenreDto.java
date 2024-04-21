package com.marky.matchflick.api.common;

import lombok.Data;

@Data
public class GenreDto {

  private String name;
  private Float score;
  private Integer decisionCount;
}
