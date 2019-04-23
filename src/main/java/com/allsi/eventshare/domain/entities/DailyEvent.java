package com.allsi.eventshare.domain.entities;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity(name = "daily_events")
public class DailyEvent extends BaseEntity{
  private Event event;

  public DailyEvent() {
  }

  @ManyToOne(targetEntity = Event.class)
  @JoinColumn(name = "event_id",
  referencedColumnName = "id")
  public Event getEvent() {
    return event;
  }

  public void setEvent(Event event) {
    this.event = event;
  }
}
