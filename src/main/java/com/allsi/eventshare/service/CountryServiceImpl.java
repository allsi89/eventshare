package com.allsi.eventshare.service;

import com.allsi.eventshare.domain.entities.Country;
import com.allsi.eventshare.domain.models.service.CountryServiceModel;
import com.allsi.eventshare.repository.CountryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CountryServiceImpl implements CountryService {
  private static final String COUNTRY_NOT_FOUND = "Country not found!";
  private final CountryRepository countryRepository;
  private final ModelMapper modelMapper;

  @Autowired
  public CountryServiceImpl(CountryRepository countryRepository, ModelMapper modelMapper) {
    this.countryRepository = countryRepository;
    this.modelMapper = modelMapper;
  }

  @Override
  public List<CountryServiceModel> findAllCountries() {
    return this.countryRepository.getAllCountriesOrOrderByNiceName()
        .stream()
        .map(c->this.modelMapper.map(c, CountryServiceModel.class))
        .collect(Collectors.toList());
  }

  @Override
  public CountryServiceModel findByCountryId(String id) {
    Country country = this.countryRepository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException(COUNTRY_NOT_FOUND));
    return this.modelMapper.map(country, CountryServiceModel.class);
  }
}
