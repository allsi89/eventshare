package com.allsi.eventshare.domain.models.view;

public class CountryViewModel {
  private String id;
  private String niceName;
  private String phonecode;

  public CountryViewModel() {
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
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

  public void setPhonecode(String phoneCode) {
    this.phonecode = phoneCode;
  }
}
