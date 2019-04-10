package com.allsi.eventshare.service;

import com.allsi.eventshare.domain.models.service.EventServiceModel;
import com.allsi.eventshare.domain.models.service.ImageServiceModel;

import java.util.List;

public interface EventService {
  EventServiceModel addEvent(EventServiceModel eventServiceModel, String username, String countryId);

  EventServiceModel findEventById(String id);

  void fillGallery(EventServiceModel eventServiceModel);

  List<EventServiceModel> findAllByCreator(String principalUsername);

  List<EventServiceModel> findAllById(List<String> eventsIds);

  void checkRegistrationForEvent(String id, String username);

  void removeAttendanceEvent(String username, String id);
}
