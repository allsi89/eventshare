package com.allsi.eventshare.domain.entities;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity(name = "images")
public class Image extends BaseEntity {
  private String url;

  public Image() {
  }

  public Image(String url) {
    this.url = url;
  }

  @Column(name = "url", nullable = false)
  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }
}
