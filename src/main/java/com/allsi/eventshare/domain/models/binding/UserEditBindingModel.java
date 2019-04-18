package com.allsi.eventshare.domain.models.binding;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import static com.allsi.eventshare.common.GlobalConstants.*;

public class UserEditBindingModel {
  private String username;
  private String email;
  private String about;

  public UserEditBindingModel() {
  }

  @NotEmpty(message = EMPTY_ERROR_MESSAGE)
  @Length(min = 5, max = 15, message = USERNAME_LENGTH_ERR_MSG)
  @Pattern(regexp = USERNAME_PATTERN, message = USERNAME_NOT_ALLOWED_CHARS_ERR_MSG)
  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  @NotEmpty(message = EMPTY_ERROR_MESSAGE)
  @Email
  @Pattern(regexp = EMAIL_PATTERN, message = INVALID_EMAIL_MSG)
  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getAbout() {
    return about;
  }

  public void setAbout(String about) {
    this.about = about;
  }
}
