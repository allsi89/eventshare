package com.allsi.eventshare.domain.entities;

import javax.persistence.*;
import java.util.List;

@Entity(name = "cities")
public class City {
  private Long id;
  private String name;
  private String postCode;
  private Country country;
  private List<Event> events;

  public City() {
  }

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", unique = true, updatable = false)
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  @Column(name = "name", nullable = false)
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Column(name = "postcode")
  public String getPostCode() {
    return postCode;
  }

  public void setPostCode(String postCode) {
    this.postCode = postCode;
  }

  @ManyToOne(targetEntity = Country.class)
  @JoinTable(name = "countries_cities",
  joinColumns = @JoinColumn(name = "city_id", referencedColumnName = "id"),
  inverseJoinColumns = @JoinColumn(name = "country_id", referencedColumnName = "id"))
  public Country getCountry() {
    return country;
  }

  public void setCountry(Country country) {
    this.country = country;
  }

  @OneToMany(targetEntity = Event.class, mappedBy = "city")
  public List<Event> getEvents() {
    return events;
  }

  public void setEvents(List<Event> events) {
    this.events = events;
  }
}
