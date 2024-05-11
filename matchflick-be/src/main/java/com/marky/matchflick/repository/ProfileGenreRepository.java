package com.marky.matchflick.repository;

import com.marky.matchflick.domain.ProfileGenreMatrix;
import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface ProfileGenreRepository extends JpaRepository<ProfileGenreMatrix, UUID> {


  @Modifying
  @Query(value = """
      insert into profile_genre_matrices (profile_id, genre_id, score)
      select :profileId, g.id, 
      case 
      when g.id in :newGenreIds then 6.5
      else 5
      end
      from genres g
      where g.id in :genres
      """, nativeQuery = true)
  void assignNewGenres(UUID profileId, List<UUID> genres, List<UUID> newGenreIds);

  @Query(value = """
      select top_genres.genre_id
      from (
          select pgm.genre_id
          from profile_genre_matrices as pgm
          where pgm.profile_id = :profileId
          order by pgm.score desc 
          LIMIT 5
      ) as top_genres
      order by RANDOM()
      LIMIT 1
      """, nativeQuery = true)
  UUID findRandomGenre(UUID profileId);

  @Modifying
  @Query(value = """
      update profile_genre_matrices 
      set score = score + :score 
      where genre_id in :genreIds
      and profile_id = :profileId
      """, nativeQuery = true)
  void updateScoresForGenres(List<UUID> genreIds, float score, UUID profileId);

  @Query("select pg from ProfileGenreMatrix pg where pg.profile.id = :profileId order by pg.score desc")
  Page<ProfileGenreMatrix> findGenres(Pageable pageable, UUID profileId);
}
