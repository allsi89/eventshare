package com.allsi.eventshare.domain.models.binding;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import static com.allsi.eventshare.constants.Constants.NULL_ERR_MSG;

public class OrganisationAddBindingModel {
  private String name;
  private String imageUrl;
  private int countryId;
  private String cityName;
  private String postCode;
  private String address;
  private String website;
  private String email;
  private String phone;

  public OrganisationAddBindingModel() {
  }

  @NotNull(message = NULL_ERR_MSG)
  @Length(min = 3, max = 50)
  public String getName() {
    return name;
  }

  public String getImageUrl() {
    return imageUrl;
  }

  public void setImageUrl(String imageUrl) {
    this.imageUrl = imageUrl;
  }

  public void setName(String name) {
    this.name = name;
  }

//  @NotNull(message = NULL_ERR_MSG)
//  @NotEmpty
//  public int getCountryId() {
//    return countryId;
//  }
//
//  public void setCountryId(int countryId) {
//    this.countryId = countryId;
//  }
//
//  @NotNull(message = NULL_ERR_MSG)
//  @NotEmpty
//  public String getCityName() {
//    return cityName;
//  }

  public void setCityName(String cityName) {
    this.cityName = cityName;
  }

  public String getPostCode() {
    return postCode;
  }

  public void setPostCode(String postCode) {
    this.postCode = postCode;
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
