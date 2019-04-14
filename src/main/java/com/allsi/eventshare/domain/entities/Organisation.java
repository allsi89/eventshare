package com.allsi.eventshare.domain.entities;


import javax.persistence.*;

@Entity(name = "organisations")
public class Organisation extends BaseEntity {
  private String name;
  private Image image;
  private Country country;
  private String city;
  private String state;
  private String zip;
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
  @JoinColumn(name = "image_id", referencedColumnName = "id")
  public Image getImage() {
    return image;
  }

  public void setImage(Image image) {
    this.image = image;
  }

  @OneToOne
  @JoinColumn(name = "country_id", referencedColumnName = "id")
  public Country getCountry() {
    return country;
  }

  public void setCountry(Country country) {
    this.country = country;
  }

  @Column(name = "city", nullable = false)
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

  @Column(name = "email", nullable = false, unique = true)
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

  @OneToOne(targetEntity = User.class)
  @JoinTable(name = "users_organisations",
  joinColumns = @JoinColumn(name = "organisation_id", referencedColumnName = "id"),
  inverseJoinColumns =  @JoinColumn(name = "user_id", referencedColumnName = "id"))
  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }
}
