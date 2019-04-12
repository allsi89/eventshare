package com.allsi.eventshare.domain.entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "categories")
public class Category extends BaseEntity {
  private String name;
  private List<Event> events;

  public Category() {
    this.events = new ArrayList<>();
  }

  @Column(name = "name", nullable = false, unique = true)
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @OneToMany(targetEntity = Event.class)
  @JoinTable(name = "events_categories",
  joinColumns = @JoinColumn(name = "category_id",
      referencedColumnName = "id"),
  inverseJoinColumns = @JoinColumn(name = "event_id",
  referencedColumnName = "id"))
  public List<Event> getEvents() {
    return events;
  }

  public void setEvents(List<Event> events) {
    this.events = events;
  }
}
