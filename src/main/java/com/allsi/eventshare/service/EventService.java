package com.allsi.eventshare.service;

import com.allsi.eventshare.domain.models.service.EventServiceModel;
import com.allsi.eventshare.domain.models.service.ImageServiceModel;

import java.util.List;

public interface EventService {
  EventServiceModel addEvent(EventServiceModel eventServiceModel, String username);

  EventServiceModel findEventById(String id);

  void fillGallery(String eventId, String username, ImageServiceModel imageServiceModel);

  List<EventServiceModel> findAllByCreator(String username);

  List<EventServiceModel> findAllByIds(List<String> eventsIds);

  EventServiceModel findEventByIdAndCreator(String eventId, String name);

  List<EventServiceModel> findAllByCountry(String countryId);

  List<EventServiceModel> findAllByCategory(String id);
}
