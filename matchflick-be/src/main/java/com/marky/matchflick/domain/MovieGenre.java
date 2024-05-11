package com.marky.matchflick.domain;

import com.marky.matchflick.domain.features.Movie;
import com.marky.matchflick.domain.ids.MovieGenreId;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "movies_genres")
@IdClass(MovieGenreId.class)
public class MovieGenre {

  @Id
  @ManyToOne(fetch = FetchType.LAZY, optional = false, cascade = CascadeType.PERSIST)
  private Movie movie;

  @Id
  @ManyToOne(fetch = FetchType.LAZY, optional = false, cascade = CascadeType.PERSIST)
  private Genre genre;
}
