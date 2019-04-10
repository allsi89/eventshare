package com.allsi.eventshare.service;

import com.allsi.eventshare.domain.entities.Country;
import com.allsi.eventshare.domain.entities.Event;
import com.allsi.eventshare.domain.entities.Image;
import com.allsi.eventshare.domain.entities.User;
import com.allsi.eventshare.domain.models.service.EventServiceModel;
import com.allsi.eventshare.repository.CountryRepository;
import com.allsi.eventshare.repository.EventRepository;
import com.allsi.eventshare.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.allsi.eventshare.constants.Constants.COUNTRY_NOT_FOUND_ERR;
import static com.allsi.eventshare.constants.Constants.USER_NOT_FOUND_ERR;

@Service
public class EventServiceImpl implements EventService {
  private static final String EVENT_NOT_FOUND = "Event not found!";
  private static final String NOT_REGISTERED_FOR_EVENT_ERR = "You are not registered to attend this event!";
  private final EventRepository eventRepository;
  private final UserRepository userRepository;
  private final CountryRepository countryRepository;
  private final ImageService imageService;
  private final ModelMapper modelMapper;

  @Autowired
  public EventServiceImpl(EventRepository eventRepository, UserRepository userRepository, CountryRepository countryRepository, ImageService imageService, ModelMapper modelMapper) {
    this.eventRepository = eventRepository;
    this.userRepository = userRepository;
    this.countryRepository = countryRepository;
    this.imageService = imageService;
    this.modelMapper = modelMapper;
  }

  @Override
  public EventServiceModel addEvent(EventServiceModel eventServiceModel, String username, String countryId) {
    User user = this.userRepository.findByUsername(username)
        .orElseThrow(() -> new UsernameNotFoundException(USER_NOT_FOUND_ERR));

    Event event = this.modelMapper.map(eventServiceModel, Event.class);

    if(event.getNotOpenToRegister()== null)
      event.setNotOpenToRegister(false);

    Country country = this.countryRepository.findById(countryId)
        .orElseThrow(() -> new IllegalArgumentException(COUNTRY_NOT_FOUND_ERR));

    event.setCreator(user);
    event.setCountry(country);

    event = this.eventRepository.saveAndFlush(event);

    return this.modelMapper.map(event, EventServiceModel.class);
  }

  @Override
  public EventServiceModel findEventById(String id) {
    Event event = this.eventRepository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException(EVENT_NOT_FOUND));

    EventServiceModel eventServiceModel = this.modelMapper
        .map(event, EventServiceModel.class);

    eventServiceModel.setNotOpenToRegister(event.getNotOpenToRegister());

    return eventServiceModel;
  }

  @Override
  public void fillGallery(EventServiceModel eventServiceModel) {
    Event event = this.eventRepository.findById(eventServiceModel.getId())
        .orElseThrow(() -> new IllegalArgumentException(EVENT_NOT_FOUND));

    event.setImages(eventServiceModel
        .getImages()
        .stream()
    .map(img -> this.modelMapper.map(img, Image.class))
    .collect(Collectors.toList()));

    this.eventRepository.saveAndFlush(event);
  }

  @Override
  public List<EventServiceModel> findAllByCreator(String principalUsername) {

    return this.eventRepository.findAllByCreator_Username(principalUsername)
        .stream()
        .map(e->this.modelMapper.map(e, EventServiceModel.class))
        .collect(Collectors.toList());
  }

  @Override
  public List<EventServiceModel> findAllById(List<String> eventsIds) {
    List<Event> events = new ArrayList<>();

    for (String id : eventsIds) {
      this.eventRepository.findById(id).ifPresent(events::add);
    }

    return events
        .stream()
        .map(e->this.modelMapper.map(e, EventServiceModel.class))
        .collect(Collectors.toList());
  }

  @Override
  public void checkRegistrationForEvent(String id, String username) {
    Event event = this.eventRepository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException(EVENT_NOT_FOUND));

    User user = this.userRepository.findByUsername(username)
        .orElseThrow(() -> new UsernameNotFoundException(USER_NOT_FOUND_ERR));

    if (!event.getAttendees().contains(user)){
      throw new IllegalArgumentException(NOT_REGISTERED_FOR_EVENT_ERR);
    }
  }

  @Override
  public void removeAttendanceEvent(String username, String id) {
    User user = this.userRepository.findByUsername(username)
        .orElseThrow(() -> new UsernameNotFoundException(USER_NOT_FOUND_ERR));

    Event event = this.eventRepository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException(EVENT_NOT_FOUND));

    List<User> attendees = event.getAttendees();

    if (!attendees.contains(user)){
      throw new IllegalArgumentException(NOT_REGISTERED_FOR_EVENT_ERR);
    }

    attendees.remove(user);

    event.setAttendees(attendees);

    this.eventRepository.saveAndFlush(event);
  }


}
