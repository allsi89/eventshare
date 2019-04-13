package com.allsi.eventshare.service;

import com.allsi.eventshare.domain.models.service.CountryServiceModel;

import java.util.List;

public interface CountryService {

  List<CountryServiceModel> findAllCountries();

  CountryServiceModel findByCountryId(String id);

  List<CountryServiceModel> findAllCountriesWithEvents();
}
