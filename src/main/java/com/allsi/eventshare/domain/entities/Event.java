package com.allsi.eventshare.domain.entities;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity(name = "events")
public class Event extends BaseEntity{
  private String name;
  private String description;
  private Country country;
  private String city;
  private String state;
  private String zip;
  private String address;
  private LocalDateTime startsOn;
  private LocalDateTime endsOn;
  private Boolean isOpenToRegister;
  private User creator;
  private List<User> attendees;
  private List<Comment> comments;
//  private List<Image> images;

  public Event() {
  }

  @Column(name = "name", nullable = false)
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Column(name = "description", columnDefinition = "TEXT")
  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  @ManyToOne(targetEntity = Country.class)
  @JoinTable(name = "events_countries",
      joinColumns = @JoinColumn(name = "event_id", referencedColumnName = "id"),
      inverseJoinColumns = @JoinColumn(name = "country_id", referencedColumnName = "id"))
  public Country getCountry() {
    return country;
  }

  public void setCountry(Country country) {
    this.country = country;
  }

  @Column(name = "city", nullable = false)
  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public String getState() {
    return state;
  }

  public void setState(String state) {
    this.state = state;
  }

  public String getZip() {
    return zip;
  }

  public void setZip(String zip) {
    this.zip = zip;
  }

  @Column(name = "address")
  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  @Column(name = "starts_on", nullable = false)
  public LocalDateTime getStartsOn() {
    return startsOn;
  }

  public void setStartsOn(LocalDateTime startsOn) {
    this.startsOn = startsOn;
  }

  @Column(name = "ends_on", nullable = false)
  public LocalDateTime getEndsOn() {
    return endsOn;
  }

  public void setEndsOn(LocalDateTime endsOn) {
    this.endsOn = endsOn;
  }

  @Column(name = "is_open_to_register", columnDefinition = "bit default 1")
  public Boolean getOpenToRegister() {
    return isOpenToRegister;
  }

  public void setOpenToRegister(Boolean openToRegister) {
    isOpenToRegister = openToRegister;
  }

  @ManyToOne(targetEntity = User.class)
  @JoinTable(name = "users_created_events",
      joinColumns = @JoinColumn(name = "event_id", referencedColumnName = "id"),
      inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"))
  public User getCreator() {
    return creator;
  }

  public void setCreator(User creator) {
    this.creator = creator;
  }

  @ManyToMany(targetEntity = User.class, mappedBy = "attendanceEvents")
  public List<User> getAttendees() {
    return attendees;
  }

  public void setAttendees(List<User> attendees) {
    this.attendees = attendees;
  }

  @OneToMany(targetEntity = Comment.class, mappedBy = "event")
  public List<Comment> getComments() {
    return comments;
  }

  public void setComments(List<Comment> comments) {
    this.comments = comments;
  }
}
