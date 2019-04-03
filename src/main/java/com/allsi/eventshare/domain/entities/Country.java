package com.allsi.eventshare.domain.entities;

import javax.persistence.*;
import java.util.List;

@Entity(name = "countries")
public class Country{
  private Integer id;
  private String iso;
  private String name;
  private String niceName;
  private String iso3;
  private String numCode;
  private String phoneCode;
  private List<City> cities;


  public Country() {
  }

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", unique = true, updatable = false)
  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  @Column(name = "iso")
  public String getIso() {
    return iso;
  }

  public void setIso(String iso) {
    this.iso = iso;
  }

  @Column(name = "name", nullable = false)
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Column(name = "nicename", nullable = false)
  public String getNiceName() {
    return niceName;
  }

  public void setNiceName(String niceName) {
    this.niceName = niceName;
  }

  @Column(name = "iso3")
  public String getIso3() {
    return iso3;
  }

  public void setIso3(String iso3) {
    this.iso3 = iso3;
  }

  @Column(name = "numcode")
  public String getNumCode() {
    return numCode;
  }

  public void setNumCode(String numCode) {
    this.numCode = numCode;
  }

  @Column(name = "phonecode", nullable = false)
  public String getPhoneCode() {
    return phoneCode;
  }

  public void setPhoneCode(String phoneCode) {
    this.phoneCode = phoneCode;
  }

  @OneToMany(targetEntity = City.class, mappedBy = "country")
  public List<City> getCities() {
    return cities;
  }

  public void setCities(List<City> cities) {
    this.cities = cities;
  }


}
