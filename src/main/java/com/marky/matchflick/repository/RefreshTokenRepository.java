package com.marky.matchflick.repository;

import com.marky.matchflick.domain.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, String> {

  @Query("""
      select case when COUNT(t.token) > 0
      then true else false end from RefreshToken t where t.token = :token
      """)
  boolean exists(String token);

  @Modifying
  @Query("delete from RefreshToken t where t.expires <= CURRENT_TIMESTAMP")
  void deleteExpired();
}
