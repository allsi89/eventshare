package com.allsi.eventshare.errors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Image not found!")
public class ImageNotFoundException extends RuntimeException{
  private int statusCode;

  public ImageNotFoundException() {
    this.statusCode = 404;
  }

  public ImageNotFoundException(String message) {
    super(message);
    this.statusCode = 404;
  }

  public int getStatusCode() {
    return statusCode;
  }
}
