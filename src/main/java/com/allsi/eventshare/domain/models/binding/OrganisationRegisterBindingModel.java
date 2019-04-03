package com.allsi.eventshare.domain.models.binding;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import static com.allsi.eventshare.constants.Constants.NULL_ERR_MSG;

public class OrganisationRegisterBindingModel {
  private String name;
  private int countryId;
  private String cityName;
  private String postCode;
  private String address;
  private String email;
  private String phone;

  @NotNull(message = NULL_ERR_MSG)
  @Length(min = 3, max = 50)
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @NotNull(message = NULL_ERR_MSG)
  @NotEmpty
  public int getCountryId() {
    return countryId;
  }

  public void setCountryId(int countryId) {
    this.countryId = countryId;
  }

  @NotNull(message = NULL_ERR_MSG)
  @NotEmpty
  public String getCityName() {
    return cityName;
  }

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

  @NotNull(message = NULL_ERR_MSG)
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
