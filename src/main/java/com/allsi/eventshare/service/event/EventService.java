package com.allsi.eventshare.service.event;

import com.allsi.eventshare.domain.models.service.EventServiceModel;
import com.allsi.eventshare.domain.models.service.ImageServiceModel;

import java.util.List;

public interface EventService {
  EventServiceModel addEvent(EventServiceModel eventServiceModel, String username);

  EventServiceModel findEventById(String id);

  void fillGallery(String eventId, String username, ImageServiceModel imageServiceModel);

  List<EventServiceModel> findAllByCreator(String username);

  EventServiceModel findEventByIdAndCreator(String eventId, String name);

  List<EventServiceModel> findAllByCountry(String countryId);

  List<EventServiceModel> findAllByCategory(String id);

  void deleteEvent(String id, String name);

  void editEvent(EventServiceModel serviceModel, String name);

  List<String> findAllCreatorIds();

  void removeImageFromGallery(String eventId, String pictureId);
}
