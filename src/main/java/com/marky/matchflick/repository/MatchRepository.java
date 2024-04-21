package com.marky.matchflick.repository;

import com.marky.matchflick.domain.Match;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MatchRepository extends JpaRepository<Match, UUID> {

  @Query("select m from Match m where m.baseEntity.id = :baseEntityId and m.sessionId = :sessionId")
  Optional<Match> findBySessionIdAndBaseEntity(UUID baseEntityId, UUID sessionId);

  Page<Match> findAllByIdIn(List<UUID> result, Pageable pageable);
}
