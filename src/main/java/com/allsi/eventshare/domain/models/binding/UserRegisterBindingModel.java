package com.allsi.eventshare.domain.models.binding;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import static com.allsi.eventshare.common.GlobalConstants.*;

public class UserRegisterBindingModel {
  private String username;
  private String password;
  private String confirmPassword;
  private String email;

  public UserRegisterBindingModel() {
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
  @Length(min = 3, max = 16, message = PASS_LENGTH_ERR_MGS)
  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  @NotEmpty(message = EMPTY_ERROR_MESSAGE)
  @Length(min = 3, max = 16, message = PASS_LENGTH_ERR_MGS)
  public String getConfirmPassword() {
    return confirmPassword;
  }

  public void setConfirmPassword(String confirmPassword) {
    this.confirmPassword = confirmPassword;
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

}
