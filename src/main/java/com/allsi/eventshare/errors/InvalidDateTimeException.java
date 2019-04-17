package com.allsi.eventshare.errors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT, reason = "Invalid datetime.")
public class InvalidDateTimeException extends RuntimeException{
  private int statusCode;

  public InvalidDateTimeException() {
    this.statusCode = 409;
  }

  public InvalidDateTimeException(String message) {
    super(message);
    this.statusCode = 409;
  }

  public int getStatusCode() {
    return statusCode;
  }
}
