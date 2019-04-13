package com.allsi.eventshare.domain.models.service;

public class CountryServiceModel extends BaseServiceModel {
  private String name;
  private String niceName;
  private String phonecode;

  public CountryServiceModel() {
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
