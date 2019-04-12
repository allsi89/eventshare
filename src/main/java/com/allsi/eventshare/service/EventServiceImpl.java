package com.allsi.eventshare.service;

import com.allsi.eventshare.domain.entities.*;
import com.allsi.eventshare.domain.models.service.EventServiceModel;
import com.allsi.eventshare.domain.models.service.ImageServiceModel;
import com.allsi.eventshare.repository.CountryRepository;
import com.allsi.eventshare.repository.EventRepository;
import com.allsi.eventshare.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import static com.allsi.eventshare.constants.Constants.COUNTRY_NOT_FOUND_ERR;
import static com.allsi.eventshare.constants.Constants.USER_NOT_FOUND_ERR;

@Service
public class EventServiceImpl implements EventService {
  private static final String DATE_TIME_STR_TO_FORMAT = "%s %s";
  private static final String EVENT_NOT_FOUND = "Event not found!";
  private static final String NOT_REGISTERED_FOR_EVENT_ERR = "You are not registered to attend this event!";
  private static final String ACCESS_DENIED_ERR = "You don't have permission to access this page!";
  private static final SimpleDateFormat DATE_FORMATTER = new SimpleDateFormat("dd-MMM-yyyy");
  private static final SimpleDateFormat TIME_FORMATTER = new SimpleDateFormat("hh:mm a");
  private static final SimpleDateFormat FORMATTER = new SimpleDateFormat("dd-MMM-yyyy hh:mm a");
  private final EventRepository eventRepository;
  private final UserRepository userRepository;
  private final CountryRepository countryRepository;
  private final ModelMapper modelMapper;

  @Autowired
  public EventServiceImpl(EventRepository eventRepository, UserRepository userRepository, CountryRepository countryRepository, ModelMapper modelMapper) {
    this.eventRepository = eventRepository;
    this.userRepository = userRepository;
    this.countryRepository = countryRepository;
    this.modelMapper = modelMapper;
  }

  @Override
  public EventServiceModel addEvent(EventServiceModel eventServiceModel, String username, String countryId) throws ParseException {
    User user = this.userRepository.findByUsername(username)
        .orElseThrow(() -> new UsernameNotFoundException(USER_NOT_FOUND_ERR));

    Date startOn = getDate(eventServiceModel);

    Event event = this.modelMapper.map(eventServiceModel, Event.class);

    event.setStartDatetime(startOn);

    Country country = this.countryRepository.findById(countryId)
        .orElseThrow(() -> new IllegalArgumentException(COUNTRY_NOT_FOUND_ERR));

    event.setCreator(user);
    event.setCountry(country);

    event = this.eventRepository.saveAndFlush(event);

    return this.modelMapper.map(event, EventServiceModel.class);
  }


  private Date getDate(EventServiceModel esm) throws ParseException {
    return FORMATTER.parse(String.format(DATE_TIME_STR_TO_FORMAT,
        esm.getStartsOnDate(),
        esm.getStartsOnTime()));
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

    this.eventRepository.saveAndFlush(event);
  }

  //TODO -- order by date -- check if mappings work
  @Override
  public List<EventServiceModel> findAllByCreator(String principalUsername) {
    List<Event> events = this.eventRepository.findAllByCreator_UsernameAndStartDatetimeAfterOrderByStartDatetime(
        principalUsername,
        new Date()
    );

    return getProcessedEvents(events);
  }

  private List<EventServiceModel> getProcessedEvents(List<Event> events) {
    return events
        .stream()
        .map(this::getEventServiceModel)
        .collect(Collectors.toList());
  }

  private EventServiceModel getEventServiceModel(Event e) {
    EventServiceModel esm = this.modelMapper.map(e, EventServiceModel.class);

    esm.setStartsOnDate(DATE_FORMATTER.format(e.getStartDatetime()));
    esm.setStartsOnTime(TIME_FORMATTER.format(e.getStartDatetime()));
    return esm;
  }

  //TODO -- order by date -- should be ordered now
  @Override
  public List<EventServiceModel> findAllById(List<String> eventsIds) {
    List<Event> events = new ArrayList<>();

    for (String id : eventsIds) {
      this.eventRepository.findById(id).ifPresent(events::add);
    }

    events = events
        .stream()
        .filter(e -> e.getStartDatetime().after(new Date()))
        .collect(Collectors.toList());

    events.sort(Comparator.comparing(Event::getStartDatetime));

    return getProcessedEvents(events);
  }

  @Override
  public void checkRegistrationForEvent(String id, String username) {
    Event event = this.eventRepository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException(EVENT_NOT_FOUND));

    User user = this.userRepository.findByUsername(username)
        .orElseThrow(() -> new UsernameNotFoundException(USER_NOT_FOUND_ERR));

    verifyUserInAttendeesList(event, user.getId());
  }

  private void verifyUserInAttendeesList(Event event, String id) {
    List<String> ids = event.getAttendees()
        .stream()
        .map(BaseEntity::getId)
        .collect(Collectors.toList());

    if (!ids.contains(id)) {
      throw new IllegalArgumentException(NOT_REGISTERED_FOR_EVENT_ERR);
    }
  }

  @Override
  public void removeAttendanceEvent(String username, String id) {
    User user = this.userRepository.findByUsername(username)
        .orElseThrow(() -> new UsernameNotFoundException(USER_NOT_FOUND_ERR));

    Event event = this.eventRepository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException(EVENT_NOT_FOUND));

    verifyUserInAttendeesList(event, user.getId());

    List<User> attendees = event.getAttendees();

    attendees.remove(user);

    event.setAttendees(attendees);

    this.eventRepository.saveAndFlush(event);
  }

  @Override
  public EventServiceModel findEventByIdAndCreator(String eventId, String username) {
    Event event = this.eventRepository.findById(eventId)
        .orElseThrow(() -> new IllegalArgumentException(EVENT_NOT_FOUND));

    validateOwnership(event.getCreator().getUsername(), username);

    return this.getEventServiceModel(event);
  }

  private void validateOwnership(String username, String requesterUsername) {
    if (!username.equals(requesterUsername)) {
      throw new AccessDeniedException(ACCESS_DENIED_ERR);
    }
  }
}
