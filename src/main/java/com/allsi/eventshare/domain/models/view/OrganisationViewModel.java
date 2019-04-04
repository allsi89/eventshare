package com.allsi.eventshare.domain.models.view;

public class OrganisationViewModel {
  private String name;
  private String country;
  private String city;
  private String cityPostCode;
  private String address;
  private String website;
  private String email;
  private String phone;

  public OrganisationViewModel() {
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
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

  public String getCityPostCode() {
    return cityPostCode;
  }

  public void setCityPostCode(String cityPostCode) {
    this.cityPostCode = cityPostCode;
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