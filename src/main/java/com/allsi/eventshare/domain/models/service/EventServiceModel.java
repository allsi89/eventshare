package com.allsi.eventshare.domain.models.service;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
public class EventServiceModel {
  private String id;
  private String name;
  private String description;
  private CountryServiceModel country;
  private String city;
  private String state;
  private String zip;
  private String address;
  private String website;
  private List<ImageServiceModel> images;
  private LocalDate startsOnDate;
  private LocalTime startsOnTime;
  private boolean isNotOpenToRegister;
  private UserServiceModel creator;
  private List<UserServiceModel> attendees;

  public EventServiceModel() {
    this.images = new ArrayList<>();
    this.attendees = new ArrayList<>();
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public CountryServiceModel getCountry() {
    return country;
  }

  public void setCountry(CountryServiceModel country) {
    this.country = country;
  }

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

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public String getWebsite() {
    return website;
  }

  public void setWebsite(String website) {
    this.website = website;
  }

  public List<ImageServiceModel> getImages() {
    return images;
  }

  public void setImages(List<ImageServiceModel> images) {
    this.images = images;
  }

  public LocalDate getStartsOnDate() {
    return startsOnDate;
  }

  public void setStartsOnDate(LocalDate startsOnDate) {
    this.startsOnDate = startsOnDate;
  }

  public LocalTime getStartsOnTime() {
    return startsOnTime;
  }

  public void setStartsOnTime(LocalTime startsOnTime) {
    this.startsOnTime = startsOnTime;
  }

  public boolean notOpenToRegister() {
    return isNotOpenToRegister;
  }

  public void setNotOpenToRegister(boolean notOpenToRegister) {
    isNotOpenToRegister = notOpenToRegister;
  }

  public UserServiceModel getCreator() {
    return creator;
  }

  public void setCreator(UserServiceModel creator) {
    this.creator = creator;
  }

  public List<UserServiceModel> getAttendees() {
    return attendees;
  }

  public void setAttendees(List<UserServiceModel> attendees) {
    this.attendees = attendees;
  }

}
