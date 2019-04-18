package com.allsi.eventshare.domain.models.binding;


import javax.validation.constraints.*;

import static com.allsi.eventshare.common.GlobalConstants.*;

public class OrganisationBindingModel {
  private String name;
  private String creatorUsername;
  private String countryId;
  private String city;
  private String state;
  private String zip;
  private String address;
  private String website;
  private String email;
  private String phone;

  public OrganisationBindingModel() {
  }

  @NotBlank(message = BLANK_ERR_MSG)
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getCreatorUsername() {
    return creatorUsername;
  }

  public void setCreatorUsername(String creatorUsername) {
    this.creatorUsername = creatorUsername;
  }

  @NotBlank(message = BLANK_ERR_MSG)
  public String getCountryId() {
    return countryId;
  }

  public void setCountryId(String countryNiceName) {
    this.countryId = countryNiceName;
  }

 @NotBlank(message = BLANK_ERR_MSG)
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

  @Email
  @Pattern(regexp = EMAIL_PATTERN, message = INVALID_EMAIL_MSG)
  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }
}
