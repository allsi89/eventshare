package com.allsi.eventshare.domain.models.binding;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import static com.allsi.eventshare.constants.Constants.NULL_ERR_MSG;
import static com.allsi.eventshare.constants.Constants.PASS_LENGTH_ERR_MGS;

public class UserRegisterBindingModel {
  private static final String USERNAME_REGEX_PATTERN = "^(?![_.])(?!.*[_.]{2})[a-z0-9._]+(?<![_.])$";
  private static final String USERNAME_LENGTH_ERR_MSG = "Username must be 5-15 symbols long.";
  private static final String USERNAME_NOT_ALLOWED_CHARS_ERR_MSG = "Invalid username. " +
      "Allowed symbols are lowercase letters, digits, '_' and '.'";
  private static final String EMPTY_ERROR_MESSAGE = "Field cannot be empty!";

  private String username;
  private String password;
  private String confirmPassword;
  private String email;

  public UserRegisterBindingModel() {
  }

  @NotNull(message = EMPTY_ERROR_MESSAGE)
  @Length(min = 5, max = 15, message = USERNAME_LENGTH_ERR_MSG)
  @Pattern(regexp = USERNAME_REGEX_PATTERN, message = USERNAME_NOT_ALLOWED_CHARS_ERR_MSG)
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
  //TODO Email Pattern
  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

}
