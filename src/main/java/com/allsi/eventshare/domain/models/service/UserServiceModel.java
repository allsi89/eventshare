package com.allsi.eventshare.domain.models.service;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class UserServiceModel {
  private String id;
  private String username;
  private String password;
  private String email;
  private String imageUrl;
  private Boolean isCorporate;
  private OrganisationServiceModel organisation;
  private List<EventServiceModel> createdEvents;
  private List<EventServiceModel> attendanceEvents;
  private List<CommentServiceModel> comments;

  private boolean isAccountNonExpired;
  private boolean isAccountNonLocked;
  private boolean isCredentialsNonExpired;
  private boolean isEnabled;
  private Set<RoleServiceModel> roles;

  public UserServiceModel() {
    this.createdEvents = new ArrayList<>();
    this.attendanceEvents = new ArrayList<>();
    this.comments = new ArrayList<>();
    this.roles = new LinkedHashSet<>();
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
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

  public String getImageUrl() {
    return imageUrl;
  }

  public void setImageUrl(String imageUrl) {
    this.imageUrl = imageUrl;
  }

  public Boolean getCorporate() {
    return isCorporate;
  }

  public void setCorporate(Boolean corporate) {
    isCorporate = corporate;
  }

  public OrganisationServiceModel getOrganisation() {
    return organisation;
  }

  public void setOrganisation(OrganisationServiceModel organisation) {
    this.organisation = organisation;
  }

  public List<EventServiceModel> getCreatedEvents() {
    return createdEvents;
  }

  public void setCreatedEvents(List<EventServiceModel> createdEvents) {
    this.createdEvents = createdEvents;
  }

  public List<EventServiceModel> getAttendanceEvents() {
    return attendanceEvents;
  }

  public void setAttendanceEvents(List<EventServiceModel> attendanceEvents) {
    this.attendanceEvents = attendanceEvents;
  }

  public List<CommentServiceModel> getComments() {
    return comments;
  }

  public void setComments(List<CommentServiceModel> comments) {
    this.comments = comments;
  }

  public boolean isAccountNonExpired() {
    return isAccountNonExpired;
  }

  public void setAccountNonExpired(boolean accountNonExpired) {
    isAccountNonExpired = accountNonExpired;
  }

  public boolean isAccountNonLocked() {
    return isAccountNonLocked;
  }

  public void setAccountNonLocked(boolean accountNonLocked) {
    isAccountNonLocked = accountNonLocked;
  }

  public boolean isCredentialsNonExpired() {
    return isCredentialsNonExpired;
  }

  public void setCredentialsNonExpired(boolean credentialsNonExpired) {
    isCredentialsNonExpired = credentialsNonExpired;
  }

  public boolean isEnabled() {
    return isEnabled;
  }

  public void setEnabled(boolean enabled) {
    isEnabled = enabled;
  }

  public Set<RoleServiceModel> getRoles() {
    return roles;
  }

  public void setRoles(Set<RoleServiceModel> roles) {
    this.roles = roles;
  }
}
