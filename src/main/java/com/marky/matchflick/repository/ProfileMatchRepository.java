package com.marky.matchflick.repository;

import com.marky.matchflick.domain.ProfileMatch;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProfileMatchRepository extends JpaRepository<ProfileMatch, UUID> {

  @Query("""
      select pm.match.baseEntity.name
      from ProfileMatch pm
      join Profile p on p.id = :profileId
      where p.currentSessionId = pm.sessionId
      and pm.match.decision = true
      group by pm.match.baseEntity.name
      having count(pm.match) = 2
      """)
  Optional<String> existMatch(UUID profileId);

  @Query("""
      select pm1.match.baseEntity.id
      from ProfileMatch pm1
      join Profile p on p.id = :profileId
      where pm1.createdAt <= current_timestamp
      and pm1.match.decision = true
      and (
            select count(pm2)
            from ProfileMatch pm2
            where pm2.match.id = pm1.match.id
            ) = 2
      order by pm1.createdAt desc
      limit 1
      """)
  Optional<UUID> findLastMatch(UUID profileId);

  @Query("""
      select distinct pm.match.id
      from ProfileMatch pm
      where pm.match.decision = true
      and pm.match.id in (
          select pm2.match.id
          from ProfileMatch pm2
          where pm2.match.decision = true
          group by pm2.match.id
          having count(pm2.match.id) = 2
      )
      and pm.profile.id = :profileId
      """)
  List<UUID> findMyMatches(UUID profileId);

  @Query("""
      select count(pm) from ProfileMatch pm
      join pm.profile.profileGenreMatrices pgm
      where pgm.genre.id = :id
      and pm.profile.id = :profileId
      and pm.match.decision = true
      """)
  Integer countMatchGenreDecision(UUID profileId, UUID id);
}