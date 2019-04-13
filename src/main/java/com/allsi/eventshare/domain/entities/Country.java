package com.allsi.eventshare.domain.entities;

import javax.persistence.*;

@Entity(name = "countries")
public class Country extends BaseEntity{
  private String name;
  private String niceName;
  private String iso3;
  private String numcode;
  private String phonecode;

  public Country() {
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
  public String getNumcode() {
    return numcode;
  }

  public void setNumcode(String numCode) {
    this.numcode = numCode;
  }

  @Column(name = "phonecode", nullable = false)
  public String getPhonecode() {
    return phonecode;
  }

  public void setPhonecode(String phoneCode) {
    this.phonecode = phoneCode;
  }

}
