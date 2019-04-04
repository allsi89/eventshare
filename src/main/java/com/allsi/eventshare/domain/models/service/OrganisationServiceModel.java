package com.allsi.eventshare.domain.models.service;

public class OrganisationServiceModel {
  private String id;
  private String name;
  private CountryServiceModel country;
  private CityServiceModel city;
  private String address;
  private String website;
  private String email;
  private String phone;

  public OrganisationServiceModel() {
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

  public CountryServiceModel getCountry() {
    return country;
  }

  public void setCountry(CountryServiceModel country) {
    this.country = country;
  }

  public CityServiceModel getCity() {
    return city;
  }

  public void setCity(CityServiceModel city) {
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
