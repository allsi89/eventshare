package com.allsi.eventshare.web.controllers;

import com.allsi.eventshare.domain.models.view.CountryViewModel;
import com.allsi.eventshare.service.CountryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("countries/")
public class CountryController extends BaseController{
  private final CountryService countryService;
  private final ModelMapper modelMapper;

  @Autowired
  public CountryController(CountryService countryService, ModelMapper modelMapper) {
    this.countryService = countryService;
    this.modelMapper = modelMapper;
  }

  @GetMapping("/fetch")
  @PreAuthorize("isAuthenticated()")
  @ResponseBody
  public List<CountryViewModel> fetchCountries() {
    return this.countryService.findAllCountries()
        .stream()
        .map(c -> this.modelMapper.map(c, CountryViewModel.class))
        .collect(Collectors.toList());
  }

}
