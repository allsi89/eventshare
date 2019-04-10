package com.allsi.eventshare.domain.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity(name = "images")
public class Image extends BaseEntity{
  private String url;
  private Event event;

  public Image() {
  }

  public Image(String url) {
    this.url = url;
  }

  @Column(nullable = false, updatable = false, columnDefinition = "TEXT")
  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  @ManyToOne(targetEntity = Event.class)
  public Event getEvent() {
    return event;
  }

  public void setEvent(Event event) {
    this.event = event;
  }
}
