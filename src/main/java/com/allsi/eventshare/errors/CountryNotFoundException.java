package com.allsi.eventshare.errors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Country not found!")
public class CountryNotFoundException extends RuntimeException {
  private int statusCode;

  public CountryNotFoundException(String message) {
    super(message);
    this.statusCode = 404;
  }

  public int getStatusCode() {
    return statusCode;
  }
}
