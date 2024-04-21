package com.marky.matchflick.domain;

import com.marky.matchflick.domain.features.BaseEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.util.UUID;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "reviews")
@EqualsAndHashCode(of = "id")
public class Review {

  @Id
  @GeneratedValue
  private UUID id;

  private Float score;

  private String title;
  private String description;

  @ManyToOne(fetch = FetchType.LAZY, optional = false, cascade = {CascadeType.PERSIST})
  private BaseEntity baseEntity;

  @ManyToOne(fetch = FetchType.LAZY, optional = false, cascade = {CascadeType.PERSIST})
  private Profile profile;
}

