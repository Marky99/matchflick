package com.marky.matchflick.domain.features;

import com.marky.matchflick.domain.Review;
import com.marky.matchflick.enums.Category;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

@Getter
@Setter
@Entity
@Table(name = "base_entities")
@EqualsAndHashCode(of = "id")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class BaseEntity {

  @Id
  @GeneratedValue
  protected UUID id;

  protected String name;
  protected String description;

  @Enumerated(EnumType.STRING)
  protected Category category;

  @CreationTimestamp
  protected LocalDateTime publicationDate;

  protected Float rating;
  protected Integer duration;
  protected String creator;

  @OneToMany(mappedBy = "baseEntity", cascade = {CascadeType.PERSIST,
      CascadeType.REMOVE}, orphanRemoval = true)
  protected List<Review> reviews;
}

