package com.marky.matchflick.api.common;

import java.util.UUID;
import lombok.Data;

@Data
public class UserDto {

  private UUID id;
  private String username;
}
