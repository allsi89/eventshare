package com.allsi.eventshare.service;

import com.allsi.eventshare.domain.models.binding.EventBindingModel;

public interface EventService {
  void addEvent(EventBindingModel bindingModel, String username);
}
