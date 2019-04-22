package com.allsi.eventshare.errors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Event not found!")
public class EventNotFoundException extends RuntimeException{
  private int statusCode;

  public EventNotFoundException(String message) {
    super(message);
    this.statusCode = 404;
  }

  public int getStatusCode() {
    return statusCode;
  }
}
