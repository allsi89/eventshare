package com.allsi.eventshare.web.controllers;

import com.allsi.eventshare.domain.models.service.*;
import com.allsi.eventshare.domain.models.view.*;
import com.allsi.eventshare.service.*;
import com.allsi.eventshare.web.annotations.PageTitle;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

import static com.allsi.eventshare.common.GlobalConstants.*;

@Controller
@RequestMapping("/explore")
public class ExploreController extends BaseController{

  private final CategoryService categoryService;
  private final CountryService countryService;
  private final OrganisationService organisationService;
  private final EventService eventService;
  private final ModelMapper modelMapper;

  @Autowired
  public ExploreController(CategoryService categoryService,
                           CountryService countryService,
                           OrganisationService organisationService,
                           EventService eventService,
                           ModelMapper modelMapper) {
    this.categoryService = categoryService;
    this.countryService = countryService;
    this.organisationService = organisationService;
    this.eventService = eventService;
    this.modelMapper = modelMapper;
  }

  @GetMapping("/options")
  @PreAuthorize("isAuthenticated()")
  @PageTitle("Explore")
  public ModelAndView explore(ModelAndView modelAndView) {
    return super.view(EXPLORE_VIEW, modelAndView);
  }

  @GetMapping("/event/{id}")
  @PreAuthorize("isAuthenticated()")
  @PageTitle("Event Details")
  public ModelAndView viewAvailableEvent(ModelAndView modelAndView,
                                     @PathVariable(name = "id") String id) {

    EventServiceModel eventServiceModel = this.eventService.findEventById(id);

    EventGuestViewModel viewModel =  this.modelMapper
        .map(eventServiceModel, EventGuestViewModel.class);

    modelAndView.addObject("viewModel", viewModel);
    return super.view(EXPLORE_EVENT_VIEW, modelAndView);
  }

  @GetMapping("/organisations/{id}")
  @PreAuthorize("isAuthenticated()")
  @PageTitle("Events By Organisations")
  public ModelAndView getOrganisationEvents(ModelAndView modelAndView,
                                            @PathVariable(name = "id") String id,
                                            Principal principal) {

    OrganisationServiceModel serviceModel = this.organisationService
        .findById(id);

    OrganisationViewModel viewModel = this.modelMapper
        .map(serviceModel,
            OrganisationViewModel.class);

    modelAndView.addObject("viewModel", viewModel);

    List<EventViewModel> events = this.eventService
        .findAllByCreator(serviceModel.getUser().getUsername())
        .stream()
        .map(e-> this.modelMapper.map(e, EventViewModel.class))
        .collect(Collectors.toList());

    modelAndView.addObject("events", events);

    return super.view(EXPLORE_ORG_EVENTS_VIEW, modelAndView);
  }

  @GetMapping("/countries/{id}")
  @PreAuthorize("isAuthenticated()")
  @PageTitle("Events By Country")
  public ModelAndView getCountryEvents(ModelAndView modelAndView,
                                       @PathVariable(name = "id") String id) {
    CountryServiceModel country = this.countryService.findByCountryId(id);

    modelAndView.addObject("countryName", country.getNiceName());

    List<EventViewModel> events = this.eventService
        .findAllByCountry(country.getId())
        .stream()
        .map(e->this.modelMapper.map(e, EventViewModel.class))
        .collect(Collectors.toList());

    modelAndView.addObject("events", events);

    return super.view(EXPLORE_COUNTRY_EVENTS_VIEW, modelAndView);
  }


  @GetMapping("/categories/{id}")
  @PreAuthorize("isAuthenticated()")
  @PageTitle("Events By Category")
  public ModelAndView getCategoryEvents(ModelAndView modelAndView,
                                       @PathVariable(name = "id") String id) {
    CategoryServiceModel category = this.categoryService.findById(id);

    modelAndView.addObject("categoryName", category.getName());

    List<EventViewModel> events = this.eventService
        .findAllByCategory(category.getId())
        .stream()
        .map(e->this.modelMapper.map(e, EventViewModel.class))
        .collect(Collectors.toList());

    modelAndView.addObject("events", events);

    return super.view(EXPLORE_CATEGORY_EVENTS_VIEW, modelAndView);
  }
}
