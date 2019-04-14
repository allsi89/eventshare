package com.allsi.eventshare.web.controllers;

import com.allsi.eventshare.domain.models.service.EventServiceModel;
import com.allsi.eventshare.domain.models.view.*;
import com.allsi.eventshare.service.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class RestApiController {
  private final CategoryService categoryService;
  private final CountryService countryService;
  private final OrganisationService organisationService;
  private final EventService eventService;
  private final ModelMapper modelMapper;

  @Autowired
  public RestApiController(CategoryService categoryService, CountryService countryService, OrganisationService organisationService, EventService eventService, ModelMapper modelMapper) {
    this.categoryService = categoryService;
    this.countryService = countryService;
    this.organisationService = organisationService;
    this.eventService = eventService;
    this.modelMapper = modelMapper;
  }

  @GetMapping("/categories/fetch-all")
  @PreAuthorize("isAuthenticated()")
  @ResponseBody
  public List<CategoryViewModel> fetchCategories() {
    return this.categoryService.findAllCategories()
        .stream()
        .map(c -> this.modelMapper.map(c, CategoryViewModel.class))
        .collect(Collectors.toList());
  }

  @GetMapping("/categories/fetch-all-with-events")
  @PreAuthorize("isAuthenticated()")
  @ResponseBody
  public List<CategoryViewModel> fetchCategoriesWithEvents() {
    return this.categoryService
        .findAllCategoriesWithEvents()
        .stream()
        .map(c -> this.modelMapper.map(c, CategoryViewModel.class))
        .collect(Collectors.toList());
  }

  @GetMapping("/countries/fetch-all")
  @PreAuthorize("isAuthenticated()")
  @ResponseBody
  public List<CountryViewModel> fetchCountries() {
    return this.countryService.findAllCountries()
        .stream()
        .map(c -> this.modelMapper.map(c, CountryViewModel.class))
        .collect(Collectors.toList());
  }

  @GetMapping("/countries/fetch-with-events")
  @PreAuthorize("isAuthenticated()")
  @ResponseBody
  public List<CountryViewModel> fetchCountries(Principal principal) {
    return this.countryService
        .findAllCountriesWithEvents(principal.getName())
        .stream()
        .map(c -> this.modelMapper.map(c, CountryViewModel.class))
        .collect(Collectors.toList());
  }

  @GetMapping("/organisations/fetch-all-with-events")
  @PreAuthorize("isAuthenticated()")
  @ResponseBody
  public List<OrganisationViewModel> fetchOrganisations(Principal principal) {
    return this.organisationService
        .findAllOrganisationsWithEvents(principal.getName())
        .stream()
        .map(o -> this.modelMapper.map(o, OrganisationViewModel.class))
        .collect(Collectors.toList());
  }

  @GetMapping(value = "events/all-pictures/{id}", produces = "application/json")
  @ResponseBody
  public Object fetchEventPictures(@PathVariable(name = "id") String id) {

    EventServiceModel eventServiceModel = this.eventService.findEventById(id);

    return eventServiceModel.getImages()
        .stream()
        .map(img -> this.modelMapper
            .map(img, ImageViewModel.class))
        .collect(Collectors.toList());
  }

  @GetMapping(value = "/events/my-events/created", produces = "application/json")
  @ResponseBody
  public Object fetchCreated(Principal principal) {
    return this.eventService.findAllByCreator(principal.getName())
        .stream()
        .map(e -> this.modelMapper.map(e, EventBriefViewModel.class))
        .collect(Collectors.toList());
  }
}
