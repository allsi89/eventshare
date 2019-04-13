package com.allsi.eventshare.domain.models.service;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class UserServiceModel extends BaseServiceModel {
  private String username;
  private String password;
  private String email;
  private String about;
  private String imageUrl;
  private Set<RoleServiceModel> roles;

  public UserServiceModel() {
    this.roles = new LinkedHashSet<>();
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

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

  public String getImageUrl() {
    return imageUrl;
  }

  public void setImageUrl(String imageUrl) {
    this.imageUrl = imageUrl;
  }

  public Set<RoleServiceModel> getRoles() {
    return roles;
  }

  public void setRoles(Set<RoleServiceModel> roles) {
    this.roles = roles;
  }
}
