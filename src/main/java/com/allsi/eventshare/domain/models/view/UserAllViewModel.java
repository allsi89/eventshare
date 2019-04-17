package com.allsi.eventshare.domain.models.view;

import java.util.HashSet;
import java.util.Set;

public class UserAllViewModel {
  private String id;
  private String username;
  private String email;
  private Set<String> authorities;

  public UserAllViewModel() {
    this.authorities = new HashSet<>();
  }

  public String getUsername() {
    return username;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public Set<String> getAuthorities() {
    return authorities;
  }

  public void setAuthorities(Set<String> authorities) {
    this.authorities = authorities;
  }
}
