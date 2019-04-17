package com.allsi.eventshare.domain.models.binding;

import javax.validation.constraints.NotBlank;

public class CategoryBindingModel {
  public String id;
  private String name;

  public CategoryBindingModel() {
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  @NotBlank
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
