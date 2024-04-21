package com.marky.matchflick.repository;

import com.marky.matchflick.domain.Review;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ReviewRepository extends JpaRepository<Review, UUID> {

  @Query("""
      select count(r) > 0 from Review r
      where r.baseEntity.id = :baseEntity
      and r.profile.id = :profileId
      """)
  boolean hasReview(UUID baseEntity, UUID profileId);

  @Query("SELECT r FROM Review r WHERE r.profile.id = :profileId")
  Page<Review> findByProfileId(UUID profileId, Pageable pageable);
}
