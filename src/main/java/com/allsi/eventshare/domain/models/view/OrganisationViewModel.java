package com.allsi.eventshare.domain.models.view;

public class OrganisationViewModel {
  private String id;
  private String name;
  private String imageUrl;
  private String countryNiceName;
  private String countryPhonecode;
  private String city;
  private String state;
  private String zip;
  private String address;
  private String website;
  private String email;
  private String phone;

  public OrganisationViewModel() {
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

  public String getImageUrl() {
    return imageUrl;
  }

  public void setImageUrl(String imageUrl) {
    this.imageUrl = imageUrl;
  }

  public String getCountryNiceName() {
    return countryNiceName;
  }

  public void setCountryNiceName(String countryNiceName) {
    this.countryNiceName = countryNiceName;
  }

  public String getCountryPhonecode() {
    return countryPhonecode;
  }

  public void setCountryPhonecode(String countryPhonecode) {
    this.countryPhonecode = countryPhonecode;
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
