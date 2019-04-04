package com.allsi.eventshare.domain.models.service;

import java.util.ArrayList;
import java.util.List;

public class CountryServiceModel {
  private int id;
  private String iso;
  private String name;
  private String niceName;
  private String iso3;
  private String numCode;
  private String phoneCode;
  private List<CityServiceModel> cities;

  public CountryServiceModel() {
    this.cities = new ArrayList<>();
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getIso() {
    return iso;
  }

  public void setIso(String iso) {
    this.iso = iso;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getNiceName() {
    return niceName;
  }

  public void setNiceName(String niceName) {
    this.niceName = niceName;
  }

  public String getIso3() {
    return iso3;
  }

  public void setIso3(String iso3) {
    this.iso3 = iso3;
  }

  public String getNumCode() {
    return numCode;
  }

  public void setNumCode(String numCode) {
    this.numCode = numCode;
  }

  public String getPhoneCode() {
    return phoneCode;
  }

  public void setPhoneCode(String phoneCode) {
    this.phoneCode = phoneCode;
  }

  public List<CityServiceModel> getCities() {
    return cities;
  }

  public void setCities(List<CityServiceModel> cities) {
    this.cities = cities;
  }
}
