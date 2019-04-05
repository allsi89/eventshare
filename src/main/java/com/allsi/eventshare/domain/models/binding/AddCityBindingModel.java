package com.allsi.eventshare.domain.models.binding;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class AddCityBindingModel {
  private String name;
  private String postCode;

  public AddCityBindingModel() {
  }

  public AddCityBindingModel(String name) {
    this.name = name;
  }

  @NotNull
  @NotEmpty
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getPostCode() {
    return postCode;
  }

  public void setPostCode(String postCode) {
    this.postCode = postCode;
  }
}
