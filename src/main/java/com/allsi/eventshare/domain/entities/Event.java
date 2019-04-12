package com.allsi.eventshare.domain.entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
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
  private String website;
  private List<Image> images;

  private Date startDatetime;
  private User creator;
  private List<User> attendees;

  public Event() {
    this.attendees = new ArrayList<>();
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

  public String getWebsite() {
    return website;
  }

  public void setWebsite(String website) {
    this.website = website;
  }

  @OneToMany
  @JoinTable(name = "events_images",
  joinColumns = @JoinColumn(name = "event_id", referencedColumnName = "id"),
  inverseJoinColumns = @JoinColumn(name = "image_id", referencedColumnName = "id"))
  public List<Image> getImages() {
    return images;
  }

  public void setImages(List<Image> images) {
    this.images = images;
  }

  @Column(name = "address")
  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "starts_on", nullable = false, columnDefinition = "TIMESTAMP")
  public Date getStartDatetime() {
    return startDatetime;
  }

  public void setStartDatetime(Date startDatetime) {
    this.startDatetime = startDatetime;
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
}
