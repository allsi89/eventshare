package com.allsi.eventshare.web.controllers;

import com.allsi.eventshare.domain.models.binding.OrganisationBindingModel;
import com.allsi.eventshare.domain.models.service.CountryServiceModel;
import com.allsi.eventshare.domain.models.service.OrganisationServiceModel;
import com.allsi.eventshare.domain.models.view.OrganisationViewModel;
import com.allsi.eventshare.service.CloudService;
import com.allsi.eventshare.service.CountryService;
import com.allsi.eventshare.service.OrganisationService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import static com.allsi.eventshare.constants.Constants.CORP;

@Controller
@RequestMapping("/organisation")
public class OrganisationController extends BaseController {
  private static final String PLUS_CHAR = "+";

  private final OrganisationService organisationService;
  private final CountryService countryService;
  private final CloudService cloudService;
  private final ModelMapper modelMapper;

  @Autowired
  public OrganisationController(OrganisationService organisationService, CountryService countryService, CloudService cloudService, ModelMapper modelMapper) {
    this.organisationService = organisationService;
    this.countryService = countryService;
    this.cloudService = cloudService;
    this.modelMapper = modelMapper;
  }

  @GetMapping("/view")
  @PreAuthorize("isAuthenticated()")
  public ModelAndView viewOrganisation(Principal principal, ModelAndView modelAndView) {

    OrganisationServiceModel organisation = this.organisationService
        .getOrganisationByUsername(principal.getName());

    OrganisationViewModel viewModel = this.modelMapper
        .map(organisation, OrganisationViewModel.class);

    CountryServiceModel country = this.countryService
        .findByCountryId(organisation.getCountry().getId());

    viewModel.setCountry(country.getNiceName());

    viewModel.setPhone(PLUS_CHAR + country.getPhonecode() + organisation.getPhone());

    modelAndView.addObject("viewModel", viewModel);

    return super.view("view-organisation", modelAndView);
  }

  @GetMapping("/add")
  @PreAuthorize("!hasRole('ROLE_CORP')")
  public ModelAndView addOrganisation(ModelAndView modelAndView,
                                      @ModelAttribute(name = "bindingModel")
                                          OrganisationBindingModel bindingModel) {

    modelAndView.addObject("bindingModel", bindingModel);
    return super.view("add-organisation", modelAndView);
  }

  @PostMapping("/add")
  @PreAuthorize("!hasRole('ROLE_CORP')")
  public ModelAndView addOrganisationConfirm(Principal principal,
                                             @Valid @ModelAttribute(name = "bindingModel")
                                                 OrganisationBindingModel bindingModel,
                                             @RequestParam("file") MultipartFile file,
                                             BindingResult bindingResult) throws IOException {
    if (!bindingResult.hasErrors()) {
      OrganisationServiceModel serviceModel = this.modelMapper
          .map(bindingModel, OrganisationServiceModel.class);

      if (!file.isEmpty()) {
        serviceModel.setImageUrl(this.cloudService.uploadImage(file));
      }

      this.countryService.findByCountryId(bindingModel.getCountry());

      if (this.organisationService
          .addOrganisation(serviceModel,
              principal.getName(),
              bindingModel.getCountry())) {

        updateAuth();
        return super.redirect("/organisation/view", CORP, true);
      }
    }

    return super.view("add-organisation");
  }

  @GetMapping("/edit")
  @PreAuthorize("isAuthenticated() AND hasRole('ROLE_CORP')")
  public ModelAndView editOrganisation(Principal principal,
                                       ModelAndView modelAndView,
                                       @ModelAttribute("bindingModel")
                                           OrganisationBindingModel bindingModel) {

    bindingModel = getOrganisationBindingModel(principal, bindingModel);

    modelAndView.addObject("bindingModel", bindingModel);
    return super.view("edit-organisation", modelAndView);
  }

  @PostMapping("/edit")
  @PreAuthorize("hasRole('ROLE_CORP')")
  public ModelAndView editOrganisationConfirm(Principal principal,
                                              @Valid @ModelAttribute("bindingModel")
                                                  OrganisationBindingModel bindingModel,
                                              @RequestParam("file") MultipartFile file,
                                              ModelAndView modelAndView,
                                              BindingResult bindingResult) {

    //TODO

    return super.redirect("/home"); // --> SUCCESS
  }

  @GetMapping("/delete")
  @PreAuthorize("isAuthenticated() AND hasRole('ROLE_CORP')")
  public ModelAndView deleteOrganisation(Principal principal,
                                       ModelAndView modelAndView,
                                       @ModelAttribute("deleteModel")
                                           OrganisationBindingModel deleteModel) {

    deleteModel = getOrganisationBindingModel(principal, deleteModel);

    modelAndView.addObject("deleteModel", deleteModel);

    return super.view("delete-organisation", modelAndView);
  }

  private void updateAuth() {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    List<GrantedAuthority> updatedAuthorities = new ArrayList<>(auth.getAuthorities());
    Authentication newAuth = new UsernamePasswordAuthenticationToken(auth.getPrincipal(), auth.getCredentials(), updatedAuthorities);
    SecurityContextHolder.getContext().setAuthentication(newAuth);
  }

  @PostMapping("/delete")
  @PreAuthorize("hasRole('ROLE_CORP')")
  public ModelAndView deleteOrganisationConfirm(Principal principal,
                                              @ModelAttribute("deleteModel")
                                                  OrganisationBindingModel deleteModel) {

    this.organisationService.deleteOrganisation(principal.getName());

    updateAuth();
    //TODO

    return super.redirect("/home", CORP, false); // --> SUCCESS
  }



  private OrganisationBindingModel getOrganisationBindingModel(Principal principal, OrganisationBindingModel bindingModel) {
    OrganisationServiceModel serviceModel = this.organisationService
        .getOrganisationByUsername(principal.getName());


    bindingModel = this.modelMapper
        .map(serviceModel, OrganisationBindingModel.class);

    CountryServiceModel country = this.countryService
        .findByCountryId(serviceModel.getCountry().getId());

    bindingModel.setCountry(country.getNiceName());
    return bindingModel;
  }
}
