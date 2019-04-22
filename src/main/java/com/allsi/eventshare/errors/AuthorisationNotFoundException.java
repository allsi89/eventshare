package com.allsi.eventshare.errors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Authorisation not found!")
public class AuthorisationNotFoundException extends RuntimeException {
  private int statusCode;

  public AuthorisationNotFoundException(String message) {
    super(message);
    this.statusCode = 404;
  }

  public int getStatusCode() {
    return statusCode;
  }
}
