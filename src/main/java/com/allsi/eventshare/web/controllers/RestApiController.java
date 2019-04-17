package com.allsi.eventshare.web.controllers;

import com.allsi.eventshare.domain.models.service.EventServiceModel;
import com.allsi.eventshare.domain.models.view.*;
import com.allsi.eventshare.service.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("/fetch")
@RestController
public class RestApiController {
  private final EventService eventService;
  private final CategoryService categoryService;
  private final CountryService countryService;
  private final OrganisationService organisationService;
  private final ModelMapper modelMapper;
  private final ImageService imageService;

  @Autowired
  public RestApiController(EventService eventService, CategoryService categoryService, CountryService countryService, OrganisationService organisationService, ModelMapper modelMapper, ImageService imageService) {
    this.eventService = eventService;
    this.categoryService = categoryService;
    this.countryService = countryService;
    this.organisationService = organisationService;
    this.modelMapper = modelMapper;
    this.imageService = imageService;
  }


  @GetMapping("/organisations-with-events")
  @PreAuthorize("isAuthenticated()")
  @ResponseBody
  public List<OrganisationBriefViewModel> fetchOrganisations(Principal principal) {
    return this.organisationService
        .findAllOrganisationsWithEvents(principal.getName())
        .stream()
        .map(o -> this.modelMapper.map(o, OrganisationBriefViewModel.class))
        .collect(Collectors.toList());
  }

  @GetMapping(value = "/created-events/all-pictures/{id}", produces = "application/json")
  @PreAuthorize("isAuthenticated()")
  @ResponseBody
  public Object fetchEventPictures(@PathVariable(name = "id") String id) {

    EventServiceModel eventServiceModel = this.eventService.findEventById(id);

    return eventServiceModel.getImages()
        .stream()
        .map(img -> this.modelMapper
            .map(img, ImageViewModel.class))
        .collect(Collectors.toList());
  }

  @GetMapping(value = "/created-events", produces = "application/json")
  @PreAuthorize("isAuthenticated()")
  @ResponseBody
  public Object fetchCreated(Principal principal) {
    return this.eventService.findAllByCreator(principal.getName())
        .stream()
        .map(e -> this.modelMapper.map(e, EventListViewModel.class))
        .collect(Collectors.toList());
  }

  @GetMapping("/countries-with-events")
  @PreAuthorize("isAuthenticated()")
  @ResponseBody
  public List<CountryViewModel> fetchCountries(Principal principal) {
    return this.countryService
        .findAllCountriesWithEvents(principal.getName())
        .stream()
        .map(c -> this.modelMapper.map(c, CountryViewModel.class))
        .collect(Collectors.toList());
  }

  @GetMapping("/all-countries")
  @PreAuthorize("isAuthenticated()")
  @ResponseBody
  public List<CountryViewModel> fetchCountries() {
    System.out.println();
    return this.countryService.findAllCountries()
        .stream()
        .map(c -> this.modelMapper.map(c, CountryViewModel.class))
        .collect(Collectors.toList());
  }

  @GetMapping("/all-categories")
  @PreAuthorize("isAuthenticated()")
  @ResponseBody
  public List<CategoryViewModel> fetchCategories() {
    return this.categoryService.findAllCategories()
        .stream()
        .map(c -> this.modelMapper.map(c, CategoryViewModel.class))
        .collect(Collectors.toList());
  }

  @GetMapping("/categories-with-events")
  @PreAuthorize("isAuthenticated()")
  @ResponseBody
  public List<CategoryViewModel> fetchCategoriesWithEvents() {
    return this.categoryService
        .findAllCategoriesWithEvents()
        .stream()
        .map(c -> this.modelMapper.map(c, CategoryViewModel.class))
        .collect(Collectors.toList());
  }


}
