package com.allsi.eventshare.domain.models.binding;

import java.time.LocalTime;

public class EventBindingModel {
  private String name;
  private String description;
  private String country;
  private String city;
  private String state;
  private String zip;
  private String address;
  private String website;
  private String startsOnDate;
  private String startsOnTime;
  private String endsOnDate;
  private String endsOnTime;
  private boolean isNotOpenToRegister;
  private String imageUrl;

  public EventBindingModel() {
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

  public String getStartsOnDate() {
    return startsOnDate;
  }

  public void setStartsOnDate(String startsOnDate) {
    this.startsOnDate = startsOnDate;
  }

  public String getStartsOnTime() {
    return startsOnTime;
  }

  public void setStartsOnTime(String startsOnTime) {
    this.startsOnTime = startsOnTime;
  }

  public String getEndsOnDate() {
    return endsOnDate;
  }

  public void setEndsOnDate(String endsOnDate) {
    this.endsOnDate = endsOnDate;
  }

  public boolean isNotOpenToRegister() {
    return isNotOpenToRegister;
  }

  public String getEndsOnTime() {
    return endsOnTime;
  }

  public void setEndsOnTime(String endsOnTime) {
    this.endsOnTime = endsOnTime;
  }

  public boolean getNotOpenToRegister() {
    return isNotOpenToRegister;
  }

  public void setNotOpenToRegister(boolean notOpenToRegister) {
    isNotOpenToRegister = notOpenToRegister;
  }

  public String getImageUrl() {
    return imageUrl;
  }

  public void setImageUrl(String imageUrl) {
    this.imageUrl = imageUrl;
  }


}
