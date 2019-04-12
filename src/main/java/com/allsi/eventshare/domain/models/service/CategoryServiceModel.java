package com.allsi.eventshare.domain.models.service;

import java.util.ArrayList;
import java.util.List;

public class CategoryServiceModel {
  private String id;
  private String name;
  private List<EventServiceModel> events;

  public CategoryServiceModel() {
    this.events = new ArrayList<>();
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public List<EventServiceModel> getEvents() {
    return events;
  }

  public void setEvents(List<EventServiceModel> events) {
    this.events = events;
  }
}
