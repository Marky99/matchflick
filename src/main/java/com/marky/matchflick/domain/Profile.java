package com.marky.matchflick.domain;


import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Getter
@Setter
@Entity
@Table(name = "profiles")
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
public class Profile {

  @Id
  @GeneratedValue
  private UUID id;

  @Column(unique = true)
  private String username;

  private String password;

  @CreationTimestamp
  private LocalDateTime createdAt;

  @UpdateTimestamp
  private LocalDateTime lastLogin;

  private UUID currentSessionId;

  @OneToMany(mappedBy = "profile", orphanRemoval = true, cascade = {CascadeType.REMOVE,
      CascadeType.PERSIST})
  private List<ProfileMatch> profileMatches;

  @OneToMany(mappedBy = "profile", orphanRemoval = true, cascade = {CascadeType.REMOVE,
      CascadeType.PERSIST})
  private List<ProfileGenreMatrix> profileGenreMatrices;

  public Profile(String username, String password) {
    this.username = username;
    this.password = password;
  }
}
