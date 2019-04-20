package com.allsi.eventshare.service;

import com.allsi.eventshare.domain.entities.Country;
import com.allsi.eventshare.domain.entities.Event;
import com.allsi.eventshare.domain.models.service.CountryServiceModel;
import com.allsi.eventshare.errors.CountryNotFoundException;
import com.allsi.eventshare.repository.CountryRepository;
import com.allsi.eventshare.repository.EventRepository;
import com.allsi.eventshare.service.country.CountryService;
import com.allsi.eventshare.service.country.CountryServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class CountryServiceTests {
  private static final String COUNTRY_ID = "countryId";
  private static final String SECOND_COUNTRY_ID = "countryId2";
  private static final String COUNTRY_NICE_NAME = "countryNiceName";
  private static final String SECOND_COUNTRY_NICE_NAME = "secondCountryNiceName";

  @Mock
  private CountryRepository countryRepository;
  @Mock
  private EventRepository eventRepository;

  @Spy
  private ModelMapper modelMapper;

  private CountryService countryService;

  @Before
  public void init() {
    this.countryService = new CountryServiceImpl(this.countryRepository,
        this.eventRepository, this.modelMapper);
  }

  @Test
  public void findAllCountries_returnsCorrect() {
    this.countryService.findAllCountries();
    verify(this.countryRepository).getAllCountriesOrderByNiceName();
  }

  @Test
  public void findByCountryId_withCorrectId_returnsCorrect() {
    Country country = new Country() {{
      setId(COUNTRY_ID);
      setNiceName(COUNTRY_NICE_NAME);
    }};

    when(this.countryRepository.findById(COUNTRY_ID))
        .thenReturn(java.util.Optional.of(country));

    CountryServiceModel serviceModel = this.countryService.findByCountryId(COUNTRY_ID);

    Assert.assertEquals(country.getId(), serviceModel.getId());
    Assert.assertEquals(country.getNiceName(), serviceModel.getNiceName());
  }

  @Test(expected = CountryNotFoundException.class)
  public void findCountryById_withNotCorrectId_throwsException() {
    this.countryService.findByCountryId(COUNTRY_ID);
  }

  @Test
  public void findAllCountriesWithEvents_withNoEvents_returnsCorrect() {
    when(this.eventRepository.findAllGroupByCountry())
        .thenReturn(new ArrayList<>());

    List<CountryServiceModel> countries = this.countryService
        .findAllCountriesWithEvents();

    Assert.assertTrue(countries.isEmpty());
  }

  @Test
  public void findAllCountriesWithEvents_withEvents_returnsCorrect() {
    Country first = new Country() {{
      setId(COUNTRY_ID);
      setName(COUNTRY_NICE_NAME);
    }};

    Country second = new Country() {{
      setId(SECOND_COUNTRY_ID);
      setName(SECOND_COUNTRY_NICE_NAME);
    }};

    List<Event> events = new ArrayList<>() {{
      add(new Event() {{
        setCountry(second);
      }});
      add(new Event() {{
        setCountry(first);
      }});
    }};

    when(this.eventRepository.findAllGroupByCountry())
        .thenReturn(events);

    List<CountryServiceModel> countries = this.countryService.findAllCountriesWithEvents();

    Assert.assertEquals(2, countries.size());
    Assert.assertEquals(first.getId(), countries.get(0).getId());
    Assert.assertEquals(second.getId(), countries.get(1).getId());
  }
}
