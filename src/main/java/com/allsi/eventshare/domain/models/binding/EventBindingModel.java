package com.allsi.eventshare.domain.models.binding;

public class EventBindingModel {
  private String name;
  private String description;
  private String countryId;
  private String city;
  private String state;
  private String zip;
  private String address;
  private String website;
  private String startsOnDate;
  private String startsOnTime;
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

  public String getCountryId() {
    return countryId;
  }

  public void setCountryId(String countryId) {
    this.countryId = countryId;
  }

  //  public String getCountry() {
//    return country;
//  }
//
//  public void setCountry(String country) {
//    this.country = country;
//  }

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

  public String getImageUrl() {
    return imageUrl;
  }

  public void setImageUrl(String imageUrl) {
    this.imageUrl = imageUrl;
  }


}
