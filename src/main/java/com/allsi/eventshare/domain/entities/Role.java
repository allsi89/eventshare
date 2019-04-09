package com.allsi.eventshare.domain.entities;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity(name = "roles")
public class Role extends BaseEntity implements GrantedAuthority {
  private String authority;

  public Role() {
  }


  public Role(String authority) {
    this.authority = authority;
  }

  @Override
  @Column(name = "authority", nullable = false)
  public String getAuthority() {
    return this.authority;
  }

  public void setAuthority(String authority) {
    this.authority = authority;
  }
}
