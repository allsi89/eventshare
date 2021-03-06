package com.allsi.eventshare.domain.models.binding;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import static com.allsi.eventshare.common.GlobalConstants.BLANK_ERR_MSG;
import static com.allsi.eventshare.common.GlobalConstants.EMPTY_ERROR_MESSAGE;

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

  @NotNull
  @NotEmpty(message = EMPTY_ERROR_MESSAGE)
  @NotBlank(message = BLANK_ERR_MSG)
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
