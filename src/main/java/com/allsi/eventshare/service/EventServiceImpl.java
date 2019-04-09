package com.allsi.eventshare.service;

import com.allsi.eventshare.domain.entities.Event;
import com.allsi.eventshare.domain.entities.User;
import com.allsi.eventshare.domain.models.binding.EventBindingModel;
import com.allsi.eventshare.domain.models.service.EventServiceModel;
import com.allsi.eventshare.repository.CountryRepository;
import com.allsi.eventshare.repository.EventRepository;
import com.allsi.eventshare.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import static com.allsi.eventshare.constants.Constants.USER_NOT_FOUND_ERR;

@Service
public class EventServiceImpl implements EventService {
  private static final String DATE_PATTERN = "dd-MMM-yyyy hh:mm a";

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
  public void addEvent(EventBindingModel bindingModel, String username) {
    User user = this.userRepository.findByUsername(username)
        .orElseThrow(() -> new UsernameNotFoundException(USER_NOT_FOUND_ERR));

    EventServiceModel eventServiceModel = this.modelMapper
        .map(bindingModel, EventServiceModel.class);


    eventServiceModel.setStartsOn(processDate(bindingModel
        .getStartsOnDate(), bindingModel.getStartsOnTime()));
    eventServiceModel.setEndsOn(processDate(bindingModel
        .getEndsOnDate(), bindingModel.getStartsOnTime()));

    Event event = this.modelMapper.map(eventServiceModel, Event.class);

    //TODO --- add event to user
    //TODO --- add country to event
    //mapping is correct right now - dates work
    System.out.println();


  }

  //WORKING
  private LocalDateTime processDate(String date, String time) {
    System.out.println();
    LocalDateTime dateTime = LocalDateTime.parse(date + " " + time,
        DateTimeFormatter.ofPattern(DATE_PATTERN));
    return dateTime;
  }
}
