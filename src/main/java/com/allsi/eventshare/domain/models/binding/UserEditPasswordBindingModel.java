package com.allsi.eventshare.domain.models.binding;

import javax.validation.constraints.NotBlank;

public class UserEditPasswordBindingModel {
  private String oldPassword;
  private String password;
  private String confirmPassword;

  public UserEditPasswordBindingModel() {
  }

  @NotBlank
  public String getOldPassword() {
    return oldPassword;
  }

  public void setOldPassword(String oldPassword) {
    this.oldPassword = oldPassword;
  }

  @NotBlank
  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  @NotBlank
  public String getConfirmPassword() {
    return confirmPassword;
  }

  public void setConfirmPassword(String confirmPassword) {
    this.confirmPassword = confirmPassword;
  }
}
