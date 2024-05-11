package com.marky.matchflick.utils;


import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.UUID;
import org.springframework.stereotype.Component;

@Component
public class StaticObjectFactory {

  private static final ZoneId ZONE = ZoneId.systemDefault();

  public Instant now() {
    return Instant.now(getClock());
  }

  public LocalDateTime nowLocalDateTime() {
    return LocalDateTime.ofInstant(Instant.now(getClock()), ZoneId.of("Europe/Prague"));
  }

  public Clock getClock() {
    return Clock.system(ZONE);
  }

  public Instant getNowAsInstant() {
    return Instant.now(getClock());
  }

  public UUID getRandomId() {
    return UUID.randomUUID();
  }

  public UUID getId() {
    return UUID.randomUUID();
  }
}
