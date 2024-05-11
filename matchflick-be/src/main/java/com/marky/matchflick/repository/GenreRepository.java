package com.marky.matchflick.repository;

import com.marky.matchflick.domain.Genre;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface GenreRepository extends JpaRepository<Genre, UUID> {

  @Query("select g.id from Genre g")
  List<UUID> findAllIds();

}
