package com.marky.matchflick.exception;

import org.springframework.http.HttpStatus;

public class UserAlreadyExistException extends MatchflickException {

  public UserAlreadyExistException(String username) {
    super("User already exist", HttpStatus.BAD_REQUEST,
        "User with username " + username + " already exists",
        "002");
  }

}

