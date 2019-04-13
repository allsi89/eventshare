package com.allsi.eventshare.service;

import com.allsi.eventshare.domain.entities.Country;
import com.allsi.eventshare.domain.entities.Event;
import com.allsi.eventshare.domain.models.service.CountryServiceModel;
import com.allsi.eventshare.repository.CountryRepository;
import com.allsi.eventshare.repository.EventRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CountryServiceImpl implements CountryService {
  private static final String COUNTRY_NOT_FOUND = "Country not found!";
  private final CountryRepository countryRepository;
  private final EventRepository eventRepository;
  private final ModelMapper modelMapper;

  @Autowired
  public CountryServiceImpl(CountryRepository countryRepository, EventRepository eventRepository, ModelMapper modelMapper) {
    this.countryRepository = countryRepository;
    this.eventRepository = eventRepository;
    this.modelMapper = modelMapper;
  }

  @Override
  public List<CountryServiceModel> findAllCountries() {
    return this.countryRepository.getAllCountriesOrOrderByNiceName()
        .stream()
        .map(c -> this.modelMapper.map(c, CountryServiceModel.class))
        .collect(Collectors.toList());
  }

  @Override
  public CountryServiceModel findByCountryId(String id) {
    Country country = this.countryRepository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException(COUNTRY_NOT_FOUND));
    return this.modelMapper.map(country, CountryServiceModel.class);
  }

  @Override
  public List<CountryServiceModel> findAllCountriesWithEvents() {
    List<Event> events = this.eventRepository.findAllGroupByCountry();

    List<CountryServiceModel> countries = new ArrayList<>();

    for (Event event : events) {
      countries.add(
          this.modelMapper
              .map(event.getCountry(), CountryServiceModel.class));
    }

    countries.sort(Comparator.comparing(CountryServiceModel::getName));

    return countries;
  }
}
