package com.allsi.eventshare.web.controllers;

import com.allsi.eventshare.domain.models.binding.OrganisationAddBindingModel;
import com.allsi.eventshare.domain.models.service.OrganisationServiceModel;
import com.allsi.eventshare.domain.models.view.OrganisationViewModel;
import com.allsi.eventshare.service.CloudService;
import com.allsi.eventshare.service.OrganisationService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.io.IOException;
import java.security.Principal;

@Controller
@RequestMapping("/organisation")
public class OrganisationController extends BaseController {
  private final OrganisationService organisationService;
  private final CloudService cloudService;
  private final ModelMapper modelMapper;

  @Autowired
  public OrganisationController(OrganisationService organisationService, CloudService cloudService, ModelMapper modelMapper) {
    this.organisationService = organisationService;
    this.cloudService = cloudService;
    this.modelMapper = modelMapper;
  }

  @GetMapping("/view")
  @PreAuthorize("hasRole('ROLE_CORP')")
  public ModelAndView viewOrganisation(Principal principal, ModelAndView modelAndView) {
    OrganisationServiceModel organisation =  this.organisationService
        .getOrganisationById(principal.getName());

    modelAndView.addObject("orgModel", this.modelMapper
        .map(organisation, OrganisationViewModel.class));

    return super.view("organisation", modelAndView);
  }

  @GetMapping("/add")
  @PreAuthorize("isAuthenticated() AND !hasRole('ROLE_CORP')")
  public ModelAndView addOrganisation(){
    return super.view("add-organisation");
  }

  @PostMapping("/add")
  @PreAuthorize("isAuthenticated() AND !hasRole('ROLE_CORP')")
  public ModelAndView addOrganisationConfirm(Principal principal,
                                             @Valid @ModelAttribute(name = "bindingModel")
                                             OrganisationAddBindingModel bindingModel,
                                             @RequestParam("file") MultipartFile file,
                                             BindingResult bindingResult) throws IOException {
    if (bindingResult.hasErrors()) {
      return super.view("add-organisation");
    }

    OrganisationServiceModel serviceModel = this.modelMapper
        .map(bindingModel, OrganisationServiceModel.class);

    if (!file.isEmpty()) {
      serviceModel.setImageUrl(this.cloudService.uploadImage(file));
    }

    boolean isSuccessful = this.organisationService
        .addOrganisation(serviceModel, principal.getName());

    if (isSuccessful) {
      return super.redirect("/organisation/view");
    }

    return super.view("add-organisation");

  }
}
