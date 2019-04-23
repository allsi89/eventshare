package com.allsi.eventshare.service;

import com.allsi.eventshare.domain.entities.Category;
import com.allsi.eventshare.domain.entities.Country;
import com.allsi.eventshare.domain.entities.Event;
import com.allsi.eventshare.domain.entities.User;
import com.allsi.eventshare.domain.models.service.*;
import com.allsi.eventshare.errors.CategoryNotFoundException;
import com.allsi.eventshare.errors.CountryNotFoundException;
import com.allsi.eventshare.errors.EventNotFoundException;
import com.allsi.eventshare.errors.IllegalOperationException;
import com.allsi.eventshare.repository.*;
import com.allsi.eventshare.service.event.EventService;
import com.allsi.eventshare.service.event.EventServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.allsi.eventshare.common.GlobalConstants.DATE_TIME_FORMAT;
import static com.allsi.eventshare.common.GlobalConstants.DATE_TIME_STR_TO_FORMAT;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class EventServiceTests {
  private static final String USER_ID = "userId";
  private static final String SECOND_USER_ID = "secondUserId";
  private static final String USER_USERNAME = "userUsername";
  private static final String SECOND_USER_USERNAME = "secondUserUsername";
  private static final String USER_PASSWORD = "userPassword";
  private static final String USER_EMAIL = "userEmail";
  private static final String COUNTRY_ID = "countryId";
  private static final String COUNTRY_NAME = "countryName";
  private static final String COUNTRY_PHONE_CODE = "countryPhoneCode";
  private static final String EVENT_NAME = "eventName";
  private static final String EVENT_ID = "eventId";
  private static final String STARTS_ON_DATE = "30-Jun-2019";
  private static final String STARTS_ON_TIME = "08:00 PM";
  private static final String CITY_NAME = "cityName";
  private static final String CATEGORY_NAME = "categoryName";
  private static final String CATEGORY_ID = "categoryId";
  private static final String IMAGE_ID = "imageId";
  private static final String IMAGE_URL = "imageUrl";

  @Mock
  private EventRepository eventRepository;
  @Mock
  private UserRepository userRepository;
  @Mock
  private CountryRepository countryRepository;
  @Mock
  private CategoryRepository categoryRepository;
  @Mock
  private DailyEventRepository dailyEventRepository;

  @Spy
  private ModelMapper modelMapper;

  private EventService eventService;

  @Before
  public void init() {
    this.eventService = new EventServiceImpl(eventRepository, userRepository,
        countryRepository, categoryRepository, dailyEventRepository, modelMapper);
    this.modelMapper = new ModelMapper();
  }

  @Test(expected = UsernameNotFoundException.class)
  public void addEvent_withNotCorrectUser_throwsException() {
    this.eventService.addEvent(null, USER_USERNAME);
  }

  @Test(expected = CountryNotFoundException.class)
  public void addEvent_withNotCorrectCountry_throwsException() {
    User user = getCreator();
    EventServiceModel eventModel = getModel();
    eventModel.setCountry(getCountry());

    when(this.userRepository.findByUsername(USER_USERNAME))
        .thenReturn(Optional.of(user));

    this.eventService.addEvent(eventModel, USER_USERNAME);
  }

  @Test(expected = CategoryNotFoundException.class)
  public void addEvent_withNotCorrectCategory_throwsException() {
    User user = getCreator();
    EventServiceModel eventModel = getModel();
    eventModel.setCountry(getCountry());
    eventModel.setCategory(getCategory());

    Country country = this.modelMapper
        .map(eventModel.getCountry(), Country.class);

    when(this.userRepository.findByUsername(USER_USERNAME))
        .thenReturn(Optional.of(user));
    when(this.countryRepository.findById(COUNTRY_ID))
        .thenReturn(Optional.ofNullable(country));

    this.eventService.addEvent(eventModel, USER_USERNAME);
  }

  @Test
  public void addEvent_withCorrectData_returnsCorrect() {
    User user = getCreator();
    EventServiceModel eventModel = getModel();
    eventModel.setCountry(getCountry());
    eventModel.setCategory(getCategory());
    eventModel.setCreator(this.modelMapper.map(user, UserServiceModel.class));

    Country country = this.modelMapper
        .map(eventModel.getCountry(), Country.class);

    Category category = this.modelMapper
        .map(eventModel.getCategory(), Category.class);

    doReturn(Optional.of(user))
        .when(this.userRepository).findByUsername(USER_USERNAME);
    doReturn(Optional.of(country))
        .when(this.countryRepository).findById(COUNTRY_ID);
    doReturn(Optional.of(category))
        .when(this.categoryRepository).findByName(CATEGORY_NAME);

    when(this.eventRepository.saveAndFlush(any()))
        .thenReturn(this.modelMapper.map(eventModel, Event.class));

    this.eventService.addEvent(eventModel, USER_USERNAME);

    verify(this.eventRepository).saveAndFlush(any());
  }

  @Test
  public void findEventById_withCorrectId_returnsCorrect() {
    Event expected = getEvent();

    when(this.eventRepository.findById(EVENT_ID))
        .thenReturn(Optional.ofNullable(expected));

    EventServiceModel actual = this.eventService.findEventById(EVENT_ID);

    assert expected != null;
    Assert.assertEquals(expected.getId(), actual.getId());

    String actualDateTime =  String.format(DATE_TIME_STR_TO_FORMAT,
        actual.getStartsOnDate(),
        actual.getStartsOnTime());

    String expectedDateTime = expected.getStartDatetime()
        .format(DateTimeFormatter.ofPattern(DATE_TIME_FORMAT));

    Assert.assertEquals(expectedDateTime, actualDateTime);
  }

  @Test(expected = EventNotFoundException.class)
  public void findEventById_withNotCorrectId_throwsException() {
    this.eventService.findEventById(EVENT_ID);
  }

  @Test
  public void fillGallery_withCorrectData_returnsCorrect() {
    Event event = getEvent();
    User user = getCreator();
    event.setCreator(user);

    ImageServiceModel image = getImage();

    doReturn(Optional.of(event))
        .when(this.eventRepository).findById(EVENT_ID);

    this.eventService.fillGallery(EVENT_ID, USER_USERNAME, image);

    verify(this.eventRepository).save(any());
  }

  @Test(expected = EventNotFoundException.class)
  public void fillGallery_withNotValidEventId_throwsException() {
    this.eventService.fillGallery(EVENT_ID, null, null);
  }

  @Test(expected = UsernameNotFoundException.class)
  public void fillGallery_withNotValidUsername_throwsException() {
    Event event = getEvent();
    User user = getCreator();
    event.setCreator(user);

    doReturn(Optional.of(event))
        .when(this.eventRepository).findById(EVENT_ID);

    this.eventService.fillGallery(EVENT_ID, SECOND_USER_USERNAME, null);
  }

  @Test
  public void findAllByCreator_withCorrectData_returnsCorrect() {
    List<Event> events = new ArrayList<>();

    User user = getCreator();

    Event event = getEvent();
    event.setCreator(user);

    events.add(event);

    when(this.eventRepository.findAllByCreator_IdOrderByStartDatetimeDesc(USER_ID))
        .thenReturn(events);

    when(this.userRepository.findByUsername(USER_USERNAME))
        .thenReturn(Optional.of(user));

    List<EventServiceModel> models = this.eventService
        .findAllByCreator(USER_USERNAME);

    Assert.assertEquals(1, models.size());
  }

  @Test(expected = UsernameNotFoundException.class)
  public void findAllByCreator_withNotValidCreator_throwsException() {
    this.eventService
        .findAllByCreator(USER_USERNAME);
  }

  @Test
  public void findByEventIdAndCreator_withValidData_returnsCorrect() {
    User user = getCreator();
    Event event = getEvent();
    event.setCreator(user);

    when(this.eventRepository.findById(EVENT_ID))
        .thenReturn(Optional.of(event));
    when(this.userRepository.findByUsername(USER_USERNAME))
        .thenReturn(Optional.of(user));

    EventServiceModel actual = this.eventService
        .findEventByIdAndCreator(EVENT_ID, USER_USERNAME);

    Assert.assertEquals(event.getId(), actual.getId());
  }

  @Test(expected = EventNotFoundException.class)
  public void findByEventIdAndCreator_withNotValidEvent_throwsException() {
    this.eventService
        .findEventByIdAndCreator(EVENT_ID, USER_USERNAME);
  }

  @Test(expected = UsernameNotFoundException.class)
  public void findByEventIdAndCreator_withNotValidUsername_throwsException() {
    Event event = getEvent();

    when(this.eventRepository.findById(EVENT_ID))
        .thenReturn(Optional.of(event));

    EventServiceModel actual = this.eventService
        .findEventByIdAndCreator(EVENT_ID, USER_USERNAME);

    Assert.assertEquals(event.getId(), actual.getId());
  }

  @Test(expected = IllegalOperationException.class)
  public void findByEventIdAndCreator_withNotValidCreator_throwsException() {
    User user = getCreator();
    Event event = getEvent();

    User creator = getCreator();
    creator.setUsername(SECOND_USER_USERNAME);
    creator.setId(SECOND_USER_ID);

    event.setCreator(creator);

    when(this.eventRepository.findById(EVENT_ID))
        .thenReturn(Optional.of(event));
    when(this.userRepository.findByUsername(USER_USERNAME))
        .thenReturn(Optional.of(user));

    EventServiceModel actual = this.eventService
        .findEventByIdAndCreator(EVENT_ID, USER_USERNAME);

    Assert.assertEquals(event.getId(), actual.getId());
  }

  @Test
  public void findAllByCountry_withValidData_returnsCorrect() {
    List<Event> events = new ArrayList<>();
    events.add(getEvent());
    when(this.eventRepository.findAllByCountry_Id(COUNTRY_ID))
        .thenReturn(events);

    List<EventServiceModel> models = this.eventService.findAllByCountry(COUNTRY_ID);

    Assert.assertEquals(1, models.size());
  }

  @Test
  public void findAllByCountry_withNotValidData_returnsEmpty() {
    List<EventServiceModel> models = this.eventService.findAllByCountry(COUNTRY_ID);
    Assert.assertTrue(models.isEmpty());
  }

  @Test
  public void findAllByCategory_withValidData_returnsCorrect() {
    List<Event> events = new ArrayList<>();
    events.add(getEvent());
    when(this.eventRepository.findAllByCountry_Id(CATEGORY_ID))
        .thenReturn(events);

    List<EventServiceModel> models = this.eventService.findAllByCountry(CATEGORY_ID);

    Assert.assertEquals(1, models.size());
  }

  @Test
  public void findAllByCategory_withNotValidData_returnsEmpty() {
    List<EventServiceModel> models = this.eventService.findAllByCountry(CATEGORY_ID);
    Assert.assertTrue(models.isEmpty());
  }

  @Test
  public void deleteEvent_withCorrectData_returnsCorrect() {
    Event event = getEvent();
    User user = getCreator();
    event.setCreator(user);

    when(this.eventRepository.findById(EVENT_ID))
        .thenReturn(Optional.of(event));

    this.eventService.deleteEvent(EVENT_ID, USER_USERNAME);

    verify(this.eventRepository).deleteById(EVENT_ID);
  }

  @Test(expected = EventNotFoundException.class)
  public void deleteEvent_withNotCorrectEventId_throwsException() {
    this.eventService.deleteEvent(EVENT_ID, USER_USERNAME);
  }

  @Test(expected = IllegalOperationException.class)
  public void deleteEvent_withCorrectNotCorrectUsername_throwsException() {
    Event event = getEvent();
    User user = getCreator();
    event.setCreator(user);

    when(this.eventRepository.findById(EVENT_ID))
        .thenReturn(Optional.of(event));

    this.eventService.deleteEvent(EVENT_ID, SECOND_USER_USERNAME);
  }

  @Test
  public void findAllCreatorsIds_withCorrectData_returnsCorrect() {
    Event event = getEvent();
    User user = getCreator();
    event.setCreator(user);

    List<Event> events = new ArrayList<>() {{
      add(event);
    }};

    when(this.eventRepository.findAllGroupByCreator())
        .thenReturn(events);

    List<String> ids = this.eventService.findAllCreatorIds();

    Assert.assertEquals(1, ids.size());
  }


  private ImageServiceModel getImage() {
    return new ImageServiceModel() {{
      setId(IMAGE_ID);
      setUrl(IMAGE_URL);
    }};
  }


  private Event getEvent() {
    Event event = new Event();
    event.setName(EVENT_NAME);
    event.setId(EVENT_ID);
    event.setStartDatetime(LocalDateTime.now().plusHours(12));
    event.setCity(CITY_NAME);
    event.setDescription("description");
    event.setState("state");
    event.setZip("zip");
    event.setAddress("address");
    event.setWebsite("website");
    return event;
  }

  private EventServiceModel getModel() {
    EventServiceModel serviceModel = new EventServiceModel();
    serviceModel.setName(EVENT_NAME);
    serviceModel.setId(EVENT_ID);
    serviceModel.setStartsOnDate(STARTS_ON_DATE);
    serviceModel.setStartsOnTime(STARTS_ON_TIME);
    serviceModel.setCity(CITY_NAME);
    serviceModel.setDescription("description");
    serviceModel.setState("state");
    serviceModel.setZip("zip");
    serviceModel.setAddress("address");
    serviceModel.setWebsite("website");
    return serviceModel;
  }


  private User getCreator() {
    return new User() {{
      setId(USER_ID);
      setUsername(USER_USERNAME);
      setEmail(USER_EMAIL);
      setPassword(USER_PASSWORD);
    }};
  }

  private CountryServiceModel getCountry() {
    return new CountryServiceModel() {{
      setId(COUNTRY_ID);
      setName(COUNTRY_NAME);
      setNiceName(COUNTRY_NAME);
      setPhonecode(COUNTRY_PHONE_CODE);
    }};
  }

  private CategoryServiceModel getCategory() {
    CategoryServiceModel category = new CategoryServiceModel();
    category.setId(CATEGORY_ID);
    category.setName(CATEGORY_NAME);
    return category;
  }
}
