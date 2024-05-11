package com.marky.matchflick.domain;

import com.marky.matchflick.domain.ids.ProfileMatchId;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

@Getter
@Setter
@Entity
@Table(name = "profile_matches")
@IdClass(ProfileMatchId.class)
public class ProfileMatch {

  @Id
  @ManyToOne(fetch = FetchType.LAZY, optional = false, cascade = {CascadeType.PERSIST,
      CascadeType.REMOVE})
  private Profile profile;

  @Id
  @ManyToOne(fetch = FetchType.LAZY, optional = false, cascade = {CascadeType.PERSIST,
      CascadeType.REMOVE})
  private Match match;

  @CreationTimestamp
  private LocalDateTime createdAt;

  private UUID sessionId;
}
