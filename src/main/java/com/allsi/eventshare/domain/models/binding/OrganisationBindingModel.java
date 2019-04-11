package com.allsi.eventshare.domain.models.binding;

import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.*;

import static com.allsi.eventshare.constants.Constants.NULL_ERR_MSG;

public class OrganisationBindingModel {
  private String name;
  private MultipartFile image;
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

  @NotNull(message = NULL_ERR_MSG)
  @Length(min = 3, max = 50)
  public String getName() {
    return name;
  }

  public MultipartFile getImage() {
    return image;
  }

  public void setImage(MultipartFile image) {
    this.image = image;
  }

  public void setName(String name) {
    this.name = name;
  }


  @NotNull(message = NULL_ERR_MSG)
  public String getCountryId() {
    return countryId;
  }

  public void setCountryId(String countryNiceName) {
    this.countryId = countryNiceName;
  }

//  public String getCountry() {
//    return country;
//  }
//
//  public void setCountry(String country) {
//    this.country = country;
//  }

  @NotNull(message = NULL_ERR_MSG)
  @NotEmpty
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
