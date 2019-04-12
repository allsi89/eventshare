package com.allsi.eventshare.domain.models.binding;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

public class CategoryBindingModel {
  private String name;

  public CategoryBindingModel() {
  }

  @NotNull
  @Length(min = 3, max = 15)
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
