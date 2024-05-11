package com.marky.matchflick.exception;

import static com.marky.matchflick.config.constants.ApplicationConstants.MATCHFLICK_ROOT;

import java.net.URI;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.spring.common.HttpStatusAdapter;

@Getter
public abstract class MatchflickException extends AbstractThrowableProblem {

  protected MatchflickException(String title, HttpStatus status, Object detail, String code) {
    super(URI.create(MATCHFLICK_ROOT + code), title, new HttpStatusAdapter(status),
        detail == null ? "" : detail.toString());
  }
}

