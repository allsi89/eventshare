package com.allsi.eventshare.service;

import com.allsi.eventshare.domain.entities.*;
import com.allsi.eventshare.domain.models.service.EventServiceModel;
import com.allsi.eventshare.domain.models.service.ImageServiceModel;
import com.allsi.eventshare.repository.CategoryRepository;
import com.allsi.eventshare.repository.CountryRepository;
import com.allsi.eventshare.repository.EventRepository;
import com.allsi.eventshare.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

import static com.allsi.eventshare.constants.Constants.*;

@Service
public class EventServiceImpl implements EventService {
  private static final String DATE_TIME_STR_TO_FORMAT = "%s %s";
  private static final String EVENT_NOT_FOUND = "Event not found!";
  private static final String ACCESS_DENIED_ERR = "You don't have permission to access this page!";
  private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd-MMM-yyyy");
  private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("hh:mm a");
  private static final String DATE_TIME_FORMAT = "dd-MMM-yyyy hh:mm a";
  private static final String CATEGORY_NOT_FOUND = "Category not found!";
  private final EventRepository eventRepository;
  private final UserRepository userRepository;
  private final CountryRepository countryRepository;
  private final CategoryRepository categoryRepository;
  private final ModelMapper modelMapper;

  @Autowired
  public EventServiceImpl(EventRepository eventRepository, UserRepository userRepository, CountryRepository countryRepository, CategoryRepository categoryRepository, ModelMapper modelMapper) {
    this.eventRepository = eventRepository;
    this.userRepository = userRepository;
    this.countryRepository = countryRepository;
    this.categoryRepository = categoryRepository;
    this.modelMapper = modelMapper;
  }

  @Override
  public EventServiceModel addEvent(EventServiceModel eventServiceModel,
                                    String username) {
    User user = this.userRepository.findByUsername(username)
        .orElseThrow(() -> new UsernameNotFoundException(USER_NOT_FOUND_ERR));

    Event event = this.modelMapper.map(eventServiceModel, Event.class);

    event.setStartDatetime(
        LocalDateTime.parse(String.format(DATE_TIME_STR_TO_FORMAT,
            eventServiceModel.getStartsOnDate(),
            eventServiceModel.getStartsOnTime()),
            DateTimeFormatter.ofPattern(DATE_TIME_FORMAT)));

    Country country = this.countryRepository.findById(eventServiceModel.getCountry().getId())
        .orElseThrow(() -> new IllegalArgumentException(COUNTRY_NOT_FOUND_ERR));

    Category category = this.categoryRepository.findByName(eventServiceModel.getCategory().getName())
        .orElseThrow(() -> new IllegalArgumentException(CATEGORY_NOT_FOUND));

    event.setCreator(user);
    event.setCountry(country);
    event.setCategory(category);

    event = this.eventRepository.saveAndFlush(event);

    return this.modelMapper.map(event, EventServiceModel.class);
  }

  @Override
  public EventServiceModel findEventById(String id) {
    Event event = this.eventRepository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException(EVENT_NOT_FOUND));

    return getEventServiceModel(event);
  }

  @Override
  public void fillGallery(String eventId, String username, ImageServiceModel imageServiceModel) {
    Event event = this.eventRepository.findById(eventId)
        .orElseThrow(() -> new IllegalArgumentException(EVENT_NOT_FOUND));

    validateOwnership(event.getCreator().getUsername(), username);

    Image image = this.modelMapper.map(imageServiceModel, Image.class);

    event.getImages().add(image);

    this.eventRepository.save(event);
  }

  @Override
  public List<EventServiceModel> findAllByCreator(String username) {
    List<Event> events = this.eventRepository
        .findAllByCreator_UsernameAndStartDatetimeAfterOrderByStartDatetime(
        username,
        LocalDateTime.now()
    );

    return getProcessedEvents(events);
  }

  private List<EventServiceModel> getProcessedEvents(List<Event> events) {
    return events
        .stream()
        .sorted((e1,e2) -> {
          if(e1.getStartDatetime().isBefore(e2.getStartDatetime())){
            return 1;
          };
          return 0;
        })
        .map(this::getEventServiceModel)
        .collect(Collectors.toList());
  }

  private EventServiceModel getEventServiceModel(Event e) {
    EventServiceModel esm = this.modelMapper.map(e, EventServiceModel.class);
    esm.setStartsOnDate(e.getStartDatetime().format(DATE_FORMATTER));
    esm.setStartsOnTime(e.getStartDatetime().format(TIME_FORMATTER));
    return esm;
  }

  @Override
  public List<EventServiceModel> findAllByIds(List<String> eventsIds) {
    List<Event> events = new ArrayList<>();

    LocalDateTime dateTime = LocalDateTime.now();

    for (String id : eventsIds) {
      this.eventRepository
          .findByIdAndStartDatetimeAfter(id, dateTime)
          .ifPresent(events::add);
    }

    events.sort(Comparator.comparing(Event::getStartDatetime));

    return getProcessedEvents(events);
  }

  @Override
  public EventServiceModel findEventByIdAndCreator(String eventId, String username) {
    Event event = this.eventRepository.findById(eventId)
        .orElseThrow(() -> new IllegalArgumentException(EVENT_NOT_FOUND));

    this.validateDate(event.getStartDatetime());

    User user = this.userRepository.findByUsername(username)
        .orElseThrow(() -> new UsernameNotFoundException(USER_NOT_FOUND_ERR));

    if(!event.getCreator().getId().equals(user.getId())){
      throw new IllegalArgumentException();//not owner error TODO
    }

    return this.getEventServiceModel(event);
  }

  @Override
  public List<EventServiceModel> findAllByCountry(String countryId) {

    List<Event> events = this.eventRepository.findAllByCountry(countryId);

    return this.getProcessedEvents(events);
  }

  @Override
  public List<EventServiceModel> findAllByCategory(String id) {
    List<Event> events = this.eventRepository.findAllByCategory(id);

    return getProcessedEvents(events);
  }

  private void validateOwnership(String username, String requesterUsername) {
    if (!username.equals(requesterUsername)) {
      throw new AccessDeniedException(ACCESS_DENIED_ERR);
    }
  }

  private void validateDate(LocalDateTime startDate) {
    if (startDate.isBefore(LocalDateTime.now())){
      throw new IllegalArgumentException(); //TODO -- event has expired error
    }
  }
}
