package com.allsi.eventshare.service;

import com.allsi.eventshare.domain.entities.Country;
import com.allsi.eventshare.domain.entities.Organisation;
import com.allsi.eventshare.domain.entities.User;
import com.allsi.eventshare.domain.models.service.ImageServiceModel;
import com.allsi.eventshare.domain.models.service.OrganisationServiceModel;
import com.allsi.eventshare.errors.CountryNotFoundException;
import com.allsi.eventshare.errors.OrganisationNotFoundException;
import com.allsi.eventshare.repository.*;
import com.allsi.eventshare.service.event.EventService;
import com.allsi.eventshare.service.organisation.OrganisationService;
import com.allsi.eventshare.service.organisation.OrganisationServiceImpl;
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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class OrganisationServiceTests {
  private static final String USER_ID = "userId";
  private static final String SECOND_USER_ID = "secondUserId";
  private static final String USER_USERNAME = "userUsername";
  private static final String SECOND_USER_USERNAME = "secondUserUsername";
  private static final String USER_PASSWORD = "userPassword";
  private static final String USER_EMAIL = "userEmail";
  private static final String COUNTRY_ID = "countryId";
  private static final String COUNTRY_NAME = "countryName";
  private static final String COUNTRY_PHONE_CODE = "countryPhoneCode";
  private static final String ORGANISATION_EMAIL = "organisationEmail";
  private static final String ORGANISATION_NAME = "organisationName";
  private static final String SECOND_ORGANISATION_NAME = "secondOrganisationName";
  private static final String ORGANISATION_CITY = "organisationCity";
  private static final String ORGANISATION_ID = "organisationId";

  @Mock
  private OrganisationRepository organisationRepository;
  @Mock
  private UserRepository userRepository;
  @Mock
  private CountryRepository countryRepository;
  @Mock
  private EventService eventService;

  @Spy
  private ModelMapper modelMapper;

  private OrganisationService organisationService;

  @Before
  public void init(){
    this.organisationService = new OrganisationServiceImpl(this.organisationRepository,
        this.userRepository, this.countryRepository, this.eventService, this.modelMapper);
  }

  @Test
  public void getOrganisationByUsername_withValidUsernameAndExistingOrganisation_returnsCorrect() {
    when(this.organisationRepository.findByUser_Username(any()))
        .thenReturn(java.util.Optional.of(new Organisation()));

    OrganisationServiceModel serviceModel = this.organisationService
        .getOrganisationByUsername(any());

    Assert.assertNotNull(serviceModel);
  }

  @Test(expected = OrganisationNotFoundException.class)
  public void getOrganisationByUsername_withNotValidData_throwsException() {
    this.organisationService.getOrganisationByUsername(any());
  }

  @Test
  public void addOrganisation_withCorrectData_returnsCorrect() {
    User owner = getOwner();
    Country country = getCountry();

    when(this.userRepository.findByUsername(owner.getUsername()))
        .thenReturn(java.util.Optional.of(owner));
    when(this.countryRepository.findById(any()))
        .thenReturn(java.util.Optional.of(country));

    OrganisationServiceModel serviceModel = new OrganisationServiceModel() {{
      setEmail(ORGANISATION_EMAIL);
      setName(ORGANISATION_NAME);
      setCity(ORGANISATION_CITY);
    }};

    this.organisationService
        .addOrganisation(serviceModel, owner.getUsername(), country.getId());

    verify(this.organisationRepository).save(any());
  }

  @Test(expected = CountryNotFoundException.class)
  public void addOrganisation_withNotCorrectCountry_throwsException() {
    this.organisationService
        .addOrganisation(new OrganisationServiceModel(), USER_USERNAME, COUNTRY_ID);
  }

  @Test(expected = UsernameNotFoundException.class)
  public void addOrganisation_withNotCorrectUser_throwsException() {
    Country country = getCountry();

    when(this.countryRepository.findById(any()))
        .thenReturn(java.util.Optional.of(country));

    this.organisationService
        .addOrganisation(new OrganisationServiceModel(), USER_USERNAME, COUNTRY_ID);
  }

  @Test
  public void deleteOrganisation_withValidData_returnsCorrect() {
    User owner = getOwner();
    Organisation organisation = new Organisation() {{
      setName(ORGANISATION_NAME);
      setId(ORGANISATION_ID);
      setUser(owner);
    }};

    when(this.organisationRepository.findByUser_Username(owner.getUsername()))
        .thenReturn(java.util.Optional.of(organisation));

    this.organisationService.deleteOrganisation(owner.getUsername());

    verify(this.organisationRepository).deleteById(organisation.getId());
  }

  @Test(expected = OrganisationNotFoundException.class)
  public void deleteOrganisation_withNotCorrectData_returnsCorrect() {
    this.organisationService.deleteOrganisation(USER_USERNAME);
  }

  @Test
  public void editOrganisation_withCorrectData_returnsCorrect() {
    User owner = getOwner();
    Organisation organisation = new Organisation() {{
      setName(ORGANISATION_NAME);
      setId(ORGANISATION_ID);
      setUser(owner);
    }};

    when(this.organisationRepository.findByUser_Username(owner.getUsername()))
        .thenReturn(java.util.Optional.of(organisation));

    Country country = getCountry();

    when(this.countryRepository.findById(country.getId()))
        .thenReturn(java.util.Optional.of(country));

    OrganisationServiceModel serviceModel = mock(OrganisationServiceModel.class);

    this.organisationService.editOrganisation(serviceModel,
        owner.getUsername(), country.getId());

    verify(this.organisationRepository).save(any());
  }

  @Test(expected = OrganisationNotFoundException.class)
  public void editOrganisation_withNotCorrectUsername_throwsException(){
    this.organisationService
        .editOrganisation(new OrganisationServiceModel(), USER_USERNAME, COUNTRY_ID);
  }

  @Test(expected = CountryNotFoundException.class)
  public void editOrganisation_withNotCorrectCountry_throwsException() {
    User owner = getOwner();
    Organisation organisation = new Organisation() {{
      setName(ORGANISATION_NAME);
      setId(ORGANISATION_ID);
      setUser(owner);
    }};

    when(this.organisationRepository.findByUser_Username(owner.getUsername()))
        .thenReturn(java.util.Optional.of(organisation));

    OrganisationServiceModel serviceModel = mock(OrganisationServiceModel.class);

    this.organisationService.editOrganisation(serviceModel, owner.getUsername(), COUNTRY_ID);
  }

  @Test
  public void editOrganisationPicture_withCorrectData_returnsCorrect() throws IOException {
    Organisation organisation = new Organisation() {{
      setName(ORGANISATION_NAME);
      setId(ORGANISATION_ID);
    }};

    when(this.organisationRepository.findByUser_Username(USER_USERNAME))
        .thenReturn(java.util.Optional.of(organisation));

    ImageServiceModel imageServiceModel = mock(ImageServiceModel.class);

    this.organisationService.editOrganisationPicture(USER_USERNAME, imageServiceModel);

    verify(this.organisationRepository).save(any());
  }

  @Test(expected = OrganisationNotFoundException.class)
  public void editOrganisationPicture_withNotCorrectOrganisation_returnsCorrect() throws IOException {
    ImageServiceModel imageServiceModel = mock(ImageServiceModel.class);

    this.organisationService.editOrganisationPicture(USER_USERNAME, imageServiceModel);

    verify(this.organisationRepository).save(any());
  }

  @Test
  public void findAllOrganisationsWithEvents_withEvents_returnsCorrect() {
    User creator = getOwner();

    Organisation first = new Organisation(){{
      setName(ORGANISATION_NAME);
      setUser(creator);
    }};

    User secondCreator = getOwner();
    secondCreator.setId(SECOND_USER_ID);
    secondCreator.setUsername(SECOND_USER_USERNAME);

    Organisation second = new Organisation() {{
      setName(SECOND_ORGANISATION_NAME);
      setUser(secondCreator);
    }};

    List<String> ids = new ArrayList<>() {{
      add(USER_ID);
      add(SECOND_USER_ID);
    }};

    when(this.eventService.findAllCreatorIds())
        .thenReturn(ids);
    when(this.organisationRepository.findByUser_Id(USER_ID))
        .thenReturn(java.util.Optional.of(first));
    when(this.organisationRepository.findByUser_Id(SECOND_USER_ID))
        .thenReturn(java.util.Optional.of(second));

    List<OrganisationServiceModel> organisations = this.organisationService
        .findAllOrganisationsWithEvents();

    Assert.assertEquals(2, organisations.size());
    Assert.assertEquals(first.getName(), organisations.get(0).getName());
    Assert.assertEquals(second.getName(), organisations.get(1).getName());
  }

  @Test
  public void findAllOrganisationsWithEvents_withNoEvents_returnsCorrect() {
    List<String> ids = new ArrayList<>();

    when(this.eventService.findAllCreatorIds())
        .thenReturn(ids);

    List<OrganisationServiceModel> organisations = this.organisationService
        .findAllOrganisationsWithEvents();

    Assert.assertTrue(organisations.isEmpty());
  }

  @Test
  public void findAllOrganisationsWithEvents_withNotCorrectOrganisations_returnsCorrect() {
    List<String> ids = new ArrayList<>() {{
      add(USER_ID);
      add(SECOND_USER_ID);
    }};

    when(this.eventService.findAllCreatorIds())
        .thenReturn(ids);

    List<OrganisationServiceModel> organisations = this.organisationService
        .findAllOrganisationsWithEvents();

    Assert.assertTrue(organisations.isEmpty());
  }

  @Test
  public void findOrganisationById_withCorrectId_returnsCorrect() {
    Organisation organisation = new Organisation(){{
      setName(ORGANISATION_NAME);
      setId(ORGANISATION_ID);
      setEmail(ORGANISATION_EMAIL);
      setCity(ORGANISATION_CITY);
    }};

    when(this.organisationRepository.findById(ORGANISATION_ID))
        .thenReturn(java.util.Optional.of(organisation));

    OrganisationServiceModel serviceModel = this.organisationService.findById(ORGANISATION_ID);

    Assert.assertEquals(organisation.getId(), serviceModel.getId());
    Assert.assertEquals(organisation.getName(), serviceModel.getName());
    Assert.assertEquals(organisation.getCity(), serviceModel.getCity());
    Assert.assertEquals(organisation.getEmail(), serviceModel.getEmail());
  }

  @Test(expected = OrganisationNotFoundException.class)
  public void findOrganisationById_withNotCorrectId_throwsException() {
    this.organisationService.findById(ORGANISATION_ID);
  }

  private Country getCountry() {
    return new Country() {{
      setId(COUNTRY_ID);
      setName(COUNTRY_NAME);
      setNiceName(COUNTRY_NAME);
      setPhonecode(COUNTRY_PHONE_CODE);
    }};
  }

  private User getOwner() {
    return new User() {{
      setId(USER_ID);
      setUsername(USER_USERNAME);
      setEmail(USER_EMAIL);
      setPassword(USER_PASSWORD);
    }};
  }
}
