package com.allsi.eventshare.web.controllers;

import com.allsi.eventshare.domain.models.view.CategoryViewModel;
import com.allsi.eventshare.domain.models.view.CountryViewModel;
import com.allsi.eventshare.domain.models.view.EventViewModel;
import com.allsi.eventshare.domain.models.view.OrganisationViewModel;
import com.allsi.eventshare.service.CategoryService;
import com.allsi.eventshare.service.CountryService;
import com.allsi.eventshare.service.EventService;
import com.allsi.eventshare.service.OrganisationService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.stream.Collectors;

import static com.allsi.eventshare.constants.GlobalConstants.EXPLORE_EVENT_VIEW;
import static com.allsi.eventshare.constants.GlobalConstants.EXPLORE_VIEW;

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
  public ModelAndView explore(ModelAndView modelAndView) {
    return super.view(EXPLORE_VIEW, modelAndView);
  }

  @GetMapping("/categories")
  @PreAuthorize("isAuthenticated()")
  @ResponseBody
  public List<CategoryViewModel> fetchCategories() {
    return this.categoryService
        .findAllCategoriesWithEvents()
        .stream()
        .map(c->this.modelMapper.map(c, CategoryViewModel.class))
        .collect(Collectors.toList());
  }

  @GetMapping("/countries")
  @PreAuthorize("isAuthenticated()")
  @ResponseBody
  public List<CountryViewModel> fetchCountries() {
    return this.countryService
        .findAllCountriesWithEvents()
        .stream()
        .map(c-> this.modelMapper.map(c, CountryViewModel.class))
        .collect(Collectors.toList());
  }

  @GetMapping("/organisations")
  @PreAuthorize("isAuthenticated()")
  @ResponseBody
  public List<OrganisationViewModel> fetchOrganisations() {
    return this.organisationService
        .findAllOrganisationsWithEvents()
        .stream()
        .map(o->this.modelMapper.map(o, OrganisationViewModel.class))
        .collect(Collectors.toList());
  }

  @GetMapping("/event/{id}")
  @PreAuthorize("isAuthenticated()")
  public ModelAndView viewEventGuest(ModelAndView modelAndView,
                                     @PathVariable(name = "id") String id) {
    EventViewModel viewModel = this.modelMapper
        .map(this.eventService.findEventById(id), EventViewModel.class);

    modelAndView.addObject("viewModel", viewModel);
    return super.view(EXPLORE_EVENT_VIEW, modelAndView);
  }


}
