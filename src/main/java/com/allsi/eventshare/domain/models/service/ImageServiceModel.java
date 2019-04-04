package com.allsi.eventshare.domain.models.service;

public class ImageServiceModel {
  private String id;
  private String url;

  public ImageServiceModel() {
  }

  public ImageServiceModel(String url) {
    this.url = url;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }
}
