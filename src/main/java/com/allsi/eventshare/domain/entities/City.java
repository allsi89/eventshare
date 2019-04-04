package com.allsi.eventshare.domain.entities;

import javax.persistence.*;
import java.util.List;

@Entity(name = "cities")
public class City extends BaseEntity {
  private String name;
  private String postCode;

  public City() {
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
}
