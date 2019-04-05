package com.allsi.eventshare.domain.models.binding;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

import static com.allsi.eventshare.constants.Constants.NULL_ERR_MSG;

public class UserEditBindingModel {
  private String username;
  private String imageUrl;
  private String email;

  public UserEditBindingModel() {
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getImageUrl() {
    return imageUrl;
  }

  public void setImageUrl(String imageUrl) {
    this.imageUrl = imageUrl;
  }

  @NotNull(message = NULL_ERR_MSG)
  @Email
  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }
}
