package com.marky.matchflick.domain.ids;

import java.io.Serializable;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(of = {"movie", "genre"})
public class MovieGenreId implements Serializable {

  private UUID movie;

  private UUID genre;
}
