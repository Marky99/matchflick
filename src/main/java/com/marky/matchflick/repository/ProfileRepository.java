package com.marky.matchflick.repository;

import com.marky.matchflick.domain.Profile;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface ProfileRepository extends JpaRepository<Profile, UUID> {

  Optional<Profile> findByUsername(String username);

  @Query("update Profile p set p.lastLogin = current_timestamp where p.username = :username")
  @Modifying
  void updateLastLogin(String username);

  boolean existsByUsername(String username);

  @Modifying
  @Query("update Profile p set p.currentSessionId = :sessionId where p.username = :username")
  void updateProfileSessionId(String username, UUID sessionId);

  @Modifying
  @Query("update Profile p set p.currentSessionId = :sessionId where p.id = :profileId")
  void updateProfileSessionId(UUID profileId, UUID sessionId);

  @Query("""
      select p from Profile p
      where p.currentSessionId =
      (select p2.currentSessionId from Profile p2 where p2.id = :profileId and p.id <> :profileId)
      """)
  Optional<Profile> findOtherSession(UUID profileId);

  @Query("""
      select p from Profile p
      join ProfileMatch pm on :profileId = pm.profile.id
      where p.id <> :profileId and pm.match.decision = true
      group by p.id
      order by count(pm.match.decision) desc
      """)
  Page<Profile> findFriends(Pageable pageable, UUID profileId);
}
