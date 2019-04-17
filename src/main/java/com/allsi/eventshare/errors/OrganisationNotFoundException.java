package com.allsi.eventshare.errors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Organisation not found!")
public class OrganisationNotFoundException extends RuntimeException {
  private int statusCode;

  public OrganisationNotFoundException() {
    this.statusCode = 404;
  }

  public OrganisationNotFoundException(String message) {
    super(message);
    this.statusCode = 404;
  }

  public int getStatusCode() {
    return statusCode;
  }
}
