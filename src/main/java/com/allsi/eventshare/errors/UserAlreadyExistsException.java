package com.allsi.eventshare.errors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT, reason = "User already exists.")
public class UserAlreadyExistsException extends RuntimeException {
  private int statusCode;

  public UserAlreadyExistsException() {
    this.statusCode = 409;
  }

  public UserAlreadyExistsException(String message) {
    super(message);
    this.statusCode = 409;
  }

  public int getStatusCode() {
    return statusCode;
  }
}
