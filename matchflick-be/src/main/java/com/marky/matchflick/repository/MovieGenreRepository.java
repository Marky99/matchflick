package com.marky.matchflick.repository;

import com.marky.matchflick.domain.MovieGenre;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MovieGenreRepository extends JpaRepository<MovieGenre, UUID> {

  @Query(value = "select mg.movie_id from movies_genres as mg where mg.genre_id = :genreId order by RANDOM() LIMIT 1", nativeQuery = true)
  UUID findRandomByGenreId(UUID genreId);
}
