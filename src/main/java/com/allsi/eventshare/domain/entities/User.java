package com.allsi.eventshare.domain.entities;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity(name = "users")
public class User implements UserDetails {
  private String id;
  private String username;
  private String password;
  private String email;
  private Boolean isCorporate;
  private Organisation organisation;
  private List<Event> createdEvents;
  private List<Event> attendanceEvents;
  private List<Comment> comments;
//  private Image image;

  private boolean isAccountNonExpired;
  private boolean isAccountNonLocked;
  private boolean isCredentialsNonExpired;
  private boolean isEnabled;
  private Set<Role> roles;

  public User() {
    this.roles = new HashSet<>();
  }

  @Id
  @GeneratedValue(generator = "uuid-string")
  @GenericGenerator(name = "uuid-string", strategy = "org.hibernate.id.UUIDGenerator")
  @Column(name = "id", unique = true, updatable = false, nullable = false)
  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  @Override
  @Column(name = "username", nullable = false, unique = true, updatable = false)
  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  @Override
  @Column(name = "password", nullable = false)
  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  @Column(name = "email", nullable = false, unique = true)
  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  @Column(name = "is_corporate", columnDefinition = "tinyint default 0")
  public Boolean getCorporate() {
    return isCorporate;
  }

  public void setCorporate(Boolean corporate) {
    isCorporate = corporate;
  }

  @OneToOne(targetEntity = Organisation.class)
  @JoinTable(name = "users_organisations",
      joinColumns = @JoinColumn(
          name = "user_id",
          referencedColumnName = "id"),
      inverseJoinColumns = @JoinColumn(
          name = "organisation_id",
          referencedColumnName = "id"))
  public Organisation getOrganisation() {
    return organisation;
  }

  public void setOrganisation(Organisation organisation) {
    this.organisation = organisation;
  }

  @OneToMany(targetEntity = Event.class, mappedBy = "creator")
  public List<Event> getCreatedEvents() {
    return createdEvents;
  }

  public void setCreatedEvents(List<Event> createdEvents) {
    this.createdEvents = createdEvents;
  }

  @ManyToMany(targetEntity = Event.class)
  @JoinTable(name = "users_attendance_events",
      joinColumns = @JoinColumn(
          name = "used_id",
          referencedColumnName = "id"),
      inverseJoinColumns = @JoinColumn(
          name = "event_id",
          referencedColumnName = "id"))
  public List<Event> getAttendanceEvents() {
    return attendanceEvents;
  }

  public void setAttendanceEvents(List<Event> attendanceEvents) {
    this.attendanceEvents = attendanceEvents;
  }

  @OneToMany(targetEntity = Comment.class, mappedBy = "user")
  public List<Comment> getComments() {
    return comments;
  }

  public void setComments(List<Comment> comments) {
    this.comments = comments;
  }

  @Override
  @Column(name = "is_account_non_expired")
  public boolean isAccountNonExpired() {
    return this.isAccountNonExpired;
  }

  public void setAccountNonExpired(boolean accountNonExpired) {
    isAccountNonExpired = accountNonExpired;
  }

  @Override
  @Column(name = "is_account_non_locked")
  public boolean isAccountNonLocked() {
    return this.isAccountNonLocked;
  }

  public void setAccountNonLocked(boolean accountNonLocked) {
    isAccountNonLocked = accountNonLocked;
  }

  @Override
  @Column(name = "is_credentials_non_expired")
  public boolean isCredentialsNonExpired() {
    return this.isCredentialsNonExpired;
  }

  public void setCredentialsNonExpired(boolean credentialsNonExpired) {
    isCredentialsNonExpired = credentialsNonExpired;
  }

  @Override
  @Column(name = "is_enabled")
  public boolean isEnabled() {
    return this.isEnabled;
  }

  public void setEnabled(boolean enabled) {
    isEnabled = enabled;
  }

  @Override
  @Transient
  public Collection<Role> getAuthorities() {
    return this.roles;
  }

  @ManyToMany(targetEntity = Role.class, fetch = FetchType.EAGER)
  @JoinTable(name = "users_roles",
      joinColumns = @JoinColumn(
          name = "user_id",
          referencedColumnName = "id"),
      inverseJoinColumns = @JoinColumn(
          name = "role_id",
          referencedColumnName = "id"))
  public Set<Role> getRoles() {
    return this.roles;
  }

  public void setRoles(Set<Role> roles) {
    this.roles = roles;
  }
}
