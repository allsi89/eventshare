package com.allsi.eventshare.domain.models.view;

import java.time.LocalDate;
import java.time.LocalTime;

public class EventBriefViewModel {
  private String id;
  private String name;
  private LocalDate startsOnDate;
  private LocalTime startsOnTime;
  private boolean isNotOpenToRegister;

  public EventBriefViewModel() {
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

  public LocalDate getStartsOnDate() {
    return startsOnDate;
  }

  public void setStartsOnDate(LocalDate startsOnDate) {
    this.startsOnDate = startsOnDate;
  }

  public LocalTime getStartsOnTime() {
    return startsOnTime;
  }

  public void setStartsOnTime(LocalTime startsOnTime) {
    this.startsOnTime = startsOnTime;
  }

  public boolean isNotOpenToRegister() {
    return isNotOpenToRegister;
  }

  public void setNotOpenToRegister(boolean notOpenToRegister) {
    isNotOpenToRegister = notOpenToRegister;
  }
}
