package com.marky.matchflick.domain;

import com.marky.matchflick.domain.features.BaseEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "matches")
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
public class Match {

  @Id
  @GeneratedValue
  private UUID id;

  private UUID sessionId;

  private boolean decision;

  @ManyToOne(fetch = FetchType.LAZY, optional = false, cascade = CascadeType.PERSIST)
  private BaseEntity baseEntity;

  @OneToMany(mappedBy = "match", orphanRemoval = true, cascade = {CascadeType.REMOVE,
      CascadeType.PERSIST})
  private List<ProfileMatch> profileMatches = new ArrayList<>();
}
