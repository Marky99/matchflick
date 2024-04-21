package com.marky.matchflick.domain;

import com.marky.matchflick.domain.ids.ProfileGenreMatrixId;
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
@Table(name = "profile_genre_matrices")
@IdClass(ProfileGenreMatrixId.class)
public class ProfileGenreMatrix {

  @Id
  @ManyToOne(fetch = FetchType.LAZY, optional = false, cascade = {CascadeType.PERSIST,
      CascadeType.REMOVE})
  private Profile profile;

  @Id
  @ManyToOne(fetch = FetchType.LAZY, optional = false, cascade = {CascadeType.PERSIST,
      CascadeType.REMOVE})
  private Genre genre;

  private Float score;
}
