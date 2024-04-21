package com.marky.matchflick.api.common;

import com.marky.matchflick.enums.Category;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;
import lombok.Data;

@Data
public class MovieDto {

  private UUID id;

  private String name;
  private String description;

  private Category category;

  private LocalDateTime publicationDate;

  private Float rating;
  private Integer duration;
  private String creator;

  private String director;
  private URL trailerLink;

  private Set<String> languages;


  private String countryOfOrigin;
}
