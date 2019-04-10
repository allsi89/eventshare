package com.allsi.eventshare.domain.models.view;

import java.time.LocalDate;
import java.time.LocalTime;

public class EventViewModel {
  private String id;
  private String name;
  private String description;
  private String country;
  private String zip;
  private String city;
  private String address;
  private String website;
  private LocalDate startsOnDate;
  private LocalTime startsOnTime;
  private boolean isNotOpenToRegister;
  private int participantCount;

  public EventViewModel() {
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

  public String getCountry() {
    return country;
  }

  public void setCountry(String country) {
    this.country = country;
  }

  public String getZip() {
    return zip;
  }

  public void setZip(String zip) {
    this.zip = zip;
  }

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
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

  public boolean isNotOpenToRegister() {
    return isNotOpenToRegister;
  }

  public void setNotOpenToRegister(boolean notOpenToRegister) {
    isNotOpenToRegister = notOpenToRegister;
  }

  public int getParticipantCount() {
    return participantCount;
  }

  public void setParticipantCount(int participantCount) {
    this.participantCount = participantCount;
  }
}
