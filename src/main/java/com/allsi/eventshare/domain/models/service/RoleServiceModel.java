package com.allsi.eventshare.domain.models.service;

public class RoleServiceModel {
  private String id;
  private String authority;

  public RoleServiceModel() {
  }

  public RoleServiceModel(String authority) {
    this.authority = authority;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getAuthority() {
    return authority;
  }

  public void setAuthority(String authority) {
    this.authority = authority;
  }
}
