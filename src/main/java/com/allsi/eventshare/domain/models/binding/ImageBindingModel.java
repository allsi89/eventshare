package com.allsi.eventshare.domain.models.binding;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import static com.allsi.eventshare.constants.Constants.REGEX_WEBSITE_PATTERN;

public class ImageBindingModel {
  private String url;

  public ImageBindingModel() {
  }

  @NotNull
  @NotEmpty
  @Pattern(regexp = REGEX_WEBSITE_PATTERN)
  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }
}
