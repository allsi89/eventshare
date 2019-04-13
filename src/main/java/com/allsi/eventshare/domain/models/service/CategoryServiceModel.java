package com.allsi.eventshare.domain.models.service;

import java.util.ArrayList;
import java.util.List;

public class CategoryServiceModel extends BaseServiceModel {
  private String name;

  public CategoryServiceModel() {
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
