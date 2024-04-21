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
@EqualsAndHashCode(of = {"profile", "match"})
public class ProfileMatchId implements Serializable {

  private UUID profile;

  private UUID match;
}
