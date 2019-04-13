package com.allsi.eventshare.domain.models.binding;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import static com.allsi.eventshare.constants.Constants.NULL_ERR_MSG;

public class UserEditBindingModel {
  private String username;
  private String email;
  private String about;

  public UserEditBindingModel() {
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  @NotNull(message = NULL_ERR_MSG)
  @NotEmpty
  @Email
  //TODO Email PAttern here too
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
