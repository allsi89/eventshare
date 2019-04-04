package com.allsi.eventshare.domain.entities;

import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity(name = "users")
public class User extends BaseEntity implements UserDetails {
  private String username;
  private String password;
  private String email;
  private Boolean isCorporate;
  private Image image;
  private List<Event> createdEvents;
  private List<Event> attendanceEvents;
  private List<Comment> comments;

  private boolean isAccountNonExpired;
  private boolean isAccountNonLocked;
  private boolean isCredentialsNonExpired;
  private boolean isEnabled;
  private Set<Role> roles;

  public User() {
    this.roles = new HashSet<>();
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

  @OneToOne(targetEntity = Image.class)
  @JoinTable(name = "users_images",
      joinColumns = @JoinColumn(
          name = "user_id",
          referencedColumnName = "id"),
      inverseJoinColumns = @JoinColumn(
          name = "image_id",
          referencedColumnName = "id"))
  public Image getImage() {
    return image;
  }

  public void setImage(Image image) {
    this.image = image;
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
