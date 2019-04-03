package com.allsi.eventshare.domain.entities;


import javax.persistence.*;

@Entity(name = "organisations")
public class Organisation extends BaseEntity{
  private String name;
  private Country country;
  private City city;
  private String address;
  private String website;
  private String email;
  private String phone;
  private User user;

  public Organisation() {
  }

  @Column(name = "name", nullable = false)
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @OneToOne
  @JoinColumn(name = "country_id", referencedColumnName = "id")
  public Country getCountry() {
    return country;
  }

  public void setCountry(Country country) {
    this.country = country;
  }

  @OneToOne(targetEntity = Country.class)
  @JoinColumn(name = "city_id", referencedColumnName = "id")
  public City getCity() {
    return city;
  }

  public void setCity(City city) {
    this.city = city;
  }

  @Column(name = "address")
  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  @Column(name = "website")
  public String getWebsite() {
    return website;
  }

  public void setWebsite(String website) {
    this.website = website;
  }

  @Column(name = "email", nullable = false)
  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  @Column(name = "phone")
  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  @OneToOne(targetEntity = User.class, mappedBy = "organisation")
  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }
}
