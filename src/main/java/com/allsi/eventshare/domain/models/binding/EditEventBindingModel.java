package com.allsi.eventshare.domain.models.binding;

import javax.validation.constraints.NotBlank;

import static com.allsi.eventshare.common.GlobalConstants.BLANK_ERR_MSG;

public class EditEventBindingModel {
  private String name;
  private String description;
  private String countryNiceName;
  private String city;
  private String state;
  private String zip;
  private String address;
  private String website;
  private String startsOnDate;
  private String startsOnTime;
  private String categoryName;

  public EditEventBindingModel() {
  }

  @NotBlank(message = BLANK_ERR_MSG)
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

  public String getCountryNiceName() {
    return countryNiceName;
  }

  public void setCountryNiceName(String countryNiceName) {
    this.countryNiceName = countryNiceName;
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

  @NotBlank(message = BLANK_ERR_MSG)
  public String getStartsOnDate() {
    return startsOnDate;
  }

  public void setStartsOnDate(String startsOnDate) {
    this.startsOnDate = startsOnDate;
  }

  @NotBlank(message = BLANK_ERR_MSG)
  public String getStartsOnTime() {
    return startsOnTime;
  }

  public void setStartsOnTime(String startsOnTime) {
    this.startsOnTime = startsOnTime;
  }

  @NotBlank(message = BLANK_ERR_MSG)
  public String getCategoryName() {
    return categoryName;
  }

  public void setCategoryName(String categoryName) {
    this.categoryName = categoryName;
  }
}
