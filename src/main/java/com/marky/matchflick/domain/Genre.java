package com.marky.matchflick.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "genres")
@EqualsAndHashCode(of = "id")
public class Genre {

  @Id
  @GeneratedValue
  private UUID id;

  private String name;
  private String description;

  @OneToMany(mappedBy = "genre", orphanRemoval = true, cascade = {CascadeType.REMOVE,
      CascadeType.PERSIST})
  private List<MovieGenre> movieGenres = new ArrayList<>();
}
