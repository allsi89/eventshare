package com.allsi.eventshare.errors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT, reason = "Invalid file.")
public class InvalidFileException extends RuntimeException {
  private int statusCode;

  public InvalidFileException(String message) {
    super(message);
    this.statusCode = 409;
  }

  public int getStatusCode() {
    return statusCode;
  }
}
