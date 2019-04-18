package com.allsi.eventshare.domain.models.binding;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;

import static com.allsi.eventshare.common.GlobalConstants.EMPTY_ERROR_MESSAGE;
import static com.allsi.eventshare.common.GlobalConstants.PASS_LENGTH_ERR_MGS;

public class UserEditPasswordBindingModel {
  private String username;
  private String oldPassword;
  private String password;
  private String confirmPassword;

  public UserEditPasswordBindingModel() {
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  @NotEmpty(message = EMPTY_ERROR_MESSAGE)
  @Length(min = 3, max = 16, message = PASS_LENGTH_ERR_MGS)
  public String getOldPassword() {
    return oldPassword;
  }

  public void setOldPassword(String oldPassword) {
    this.oldPassword = oldPassword;
  }

  @NotEmpty(message = EMPTY_ERROR_MESSAGE)
  @Length(min = 3, max = 16, message = PASS_LENGTH_ERR_MGS)
  public String getPassword() {
    return password;
  }

  public void setPassword(String newPassword) {
    this.password = newPassword;
  }

  @NotEmpty(message = EMPTY_ERROR_MESSAGE)
  @Length(min = 3, max = 16, message = PASS_LENGTH_ERR_MGS)
  public String getConfirmPassword() {
    return confirmPassword;
  }

  public void setConfirmPassword(String confirmPassword) {
    this.confirmPassword = confirmPassword;
  }
}
