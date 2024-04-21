package com.marky.matchflick.exception;

import org.springframework.http.HttpStatus;

public class EntityNotFoundException extends MatchflickException {

  public EntityNotFoundException(Class<?> clazz) {
    super("Entity not found", HttpStatus.NOT_FOUND,
        "Entity of class " + clazz.getSimpleName() + " is not found",
        "001");
  }

}

