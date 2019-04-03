package com.allsi.eventshare.domain.models.binding;

import java.time.LocalDateTime;

public class EventAddBindingModel {
  private String name;
  private String description;
  private int countryId;
  private String cityName;
  private String address;
  private LocalDateTime startsOn;
  private LocalDateTime endsOn;
  private Boolean isOpenToRegister;
  private String campaignId;
  private String creatorId;
}
