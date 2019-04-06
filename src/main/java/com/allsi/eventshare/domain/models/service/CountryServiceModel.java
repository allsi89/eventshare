package com.allsi.eventshare.domain.models.service;

public class CountryServiceModel {
  private String id;
  private String name;
  private String niceName;
  private String phonecode;

  public CountryServiceModel() {
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

  public String getNiceName() {
    return niceName;
  }

  public void setNiceName(String niceName) {
    this.niceName = niceName;
  }

  public String getPhonecode() {
    return phonecode;
  }

  public void setPhonecode(String phonecode) {
    this.phonecode = phonecode;
  }
}
