package com.allsi.eventshare.domain.entities;

import javax.persistence.*;

@Entity(name = "categories")
public class Category extends BaseEntity {
  private String name;

  public Category() {
  }

  @Column(name = "name", nullable = false, unique = true)
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
