package com.allsi.eventshare.domain.models.service;


import java.util.ArrayList;
import java.util.List;
public class EventServiceModel extends BaseServiceModel{
  private String name;
  private String description;
  private CountryServiceModel country;
  private String city;
  private String state;
  private String zip;
  private String address;
  private String website;
  private List<ImageServiceModel> images;
  private String startsOnDate;
  private String startsOnTime;
  private CategoryServiceModel category;
  private UserServiceModel creator;
  private List<UserServiceModel> attendees;

  public EventServiceModel() {
    this.images = new ArrayList<>();
    this.attendees = new ArrayList<>();
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

  public CategoryServiceModel getCategory() {
    return category;
  }

  public void setCategory(CategoryServiceModel category) {
    this.category = category;
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
