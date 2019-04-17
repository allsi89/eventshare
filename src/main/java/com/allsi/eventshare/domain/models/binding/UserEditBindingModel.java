package com.allsi.eventshare.domain.models.binding;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class UserEditBindingModel {
  private String username;
  private String email;
  private String about;

  public UserEditBindingModel() {
  }

  @NotBlank
  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  @NotBlank
  @Email
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
