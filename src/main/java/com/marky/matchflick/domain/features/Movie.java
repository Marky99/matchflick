package com.marky.matchflick.domain.features;


import com.marky.matchflick.domain.MovieGenre;
import jakarta.persistence.CascadeType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.net.URL;
import java.util.List;
import java.util.Set;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "movies")
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class Movie extends BaseEntity {

  private String director;
  private URL trailerLink;

  @ElementCollection(targetClass = String.class)
  @CollectionTable(name = "movie_languages", joinColumns = @JoinColumn(name = "movie_id"))
  @Column(name = "language")
  private Set<String> languages;

  private String countryOfOrigin;

  @OneToMany(mappedBy = "movie", orphanRemoval = true, cascade = {CascadeType.REMOVE,
      CascadeType.PERSIST})
  private List<MovieGenre> movieGenre;
}
