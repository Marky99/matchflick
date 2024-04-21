package com.marky.matchflick.repository;

import com.marky.matchflick.domain.features.Movie;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MovieRepository extends JpaRepository<Movie, UUID> {

  @Query("select m from Movie m order by function('RANDOM') limit 1")
  Optional<Movie> findRandomMovie();

}
