package com.allsi.eventshare.web.controllers;

import com.allsi.eventshare.domain.models.binding.OrganisationBindingModel;
import com.allsi.eventshare.domain.models.service.ImageServiceModel;
import com.allsi.eventshare.domain.models.service.OrganisationServiceModel;
import com.allsi.eventshare.domain.models.view.OrganisationViewModel;
import com.allsi.eventshare.service.ImageService;
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
  private static final String PLUS_CHAR = "+";

  private final OrganisationService organisationService;
  private final ImageService imageService;
  private final ModelMapper modelMapper;

  @Autowired
  public OrganisationController(OrganisationService organisationService, ImageService imageService, ModelMapper modelMapper) {
    this.organisationService = organisationService;
    this.imageService = imageService;
    this.modelMapper = modelMapper;
  }

  @GetMapping("/view")
  @PreAuthorize("isAuthenticated() AND hasRole('ROLE_CORP')")
  public ModelAndView viewOrganisation(Principal principal, ModelAndView modelAndView) {

    OrganisationServiceModel organisation = this.organisationService
        .getOrganisationByUsername(principal.getName());

    OrganisationViewModel viewModel = this.modelMapper
        .map(organisation, OrganisationViewModel.class);

    viewModel.setCountry(organisation.getCountry().getNiceName());

    viewModel.setPhone(PLUS_CHAR + organisation.getCountry().getPhonecode() + organisation.getPhone());

    modelAndView.addObject("viewModel", viewModel);

    return super.view("view-organisation", modelAndView);
  }

  @GetMapping("/add")
  @PreAuthorize("isAuthenticated() AND !hasRole('ROLE_CORP')")
  public ModelAndView addOrganisation(ModelAndView modelAndView,
                                      @ModelAttribute(name = "bindingModel")
                                          OrganisationBindingModel bindingModel) {

    modelAndView.addObject("bindingModel", bindingModel);
    return super.view("add-organisation", modelAndView);
  }

  @PostMapping("/add")
  @PreAuthorize("isAuthenticated() AND !hasRole('ROLE_CORP')")
  public ModelAndView addOrganisationConfirm(Principal principal,
                                             @Valid @ModelAttribute(name = "bindingModel")
                                                 OrganisationBindingModel bindingModel,
                                             BindingResult bindingResult,
                                             ModelAndView modelAndView) throws IOException {
    if (!bindingResult.hasErrors()) {

      OrganisationServiceModel serviceModel = this.modelMapper
          .map(bindingModel, OrganisationServiceModel.class);

      this.organisationService
          .addOrganisation(serviceModel, principal.getName(), bindingModel.getCountry());

      return super.redirect("/organisation/view", true);
    }

    modelAndView.addObject("bindingModel", bindingModel);
    return super.view("add-organisation");
  }

  @GetMapping("/edit")
  @PreAuthorize("isAuthenticated() AND hasRole('ROLE_CORP')")
  public ModelAndView editOrganisation(Principal principal,
                                       ModelAndView modelAndView,
                                       @ModelAttribute("bindingModel")
                                           OrganisationBindingModel bindingModel) {

    modelAndView.addObject("bindingModel",
        getOrganisationBindingModel(principal.getName()));

    return super.view("edit-organisation", modelAndView);
  }

  @PostMapping("/edit")
  @PreAuthorize("hasRole('ROLE_CORP')")
  public ModelAndView editOrganisationConfirm(Principal principal,
                                              @Valid @ModelAttribute("bindingModel")
                                                  OrganisationBindingModel bindingModel,
                                              ModelAndView modelAndView,
                                              BindingResult bindingResult) {
    if (!bindingResult.hasErrors()) {
      OrganisationServiceModel serviceModel = this.modelMapper
          .map(bindingModel, OrganisationServiceModel.class);

      this.organisationService
          .editOrganisation(serviceModel, principal.getName(), bindingModel.getCountry());

      return super.redirect("/organisation/view");
    }

    modelAndView.addObject("bindingModel", bindingModel);

    return super.view("edit-organisation", modelAndView);
  }

  @GetMapping("/delete")
  @PreAuthorize("isAuthenticated() AND hasRole('ROLE_CORP')")
  public ModelAndView deleteOrganisation(Principal principal,
                                         ModelAndView modelAndView,
                                         @ModelAttribute("deleteModel")
                                             OrganisationBindingModel deleteModel) {

    modelAndView.addObject("deleteModel",
        getOrganisationBindingModel(principal.getName()));

    return super.view("delete-organisation", modelAndView);
  }

  @PostMapping("/delete")
  @PreAuthorize("hasRole('ROLE_CORP')")
  public ModelAndView deleteOrganisationConfirm(Principal principal,
                                                @ModelAttribute("deleteModel")
                                                    OrganisationBindingModel deleteModel) {

    this.organisationService.deleteOrganisation(principal.getName());
    return super.redirect("/home", false);
  }


  private OrganisationBindingModel getOrganisationBindingModel(String name) {
    OrganisationServiceModel serviceModel = this.organisationService
        .getOrganisationByUsername(name);

    OrganisationBindingModel bindingModel = this.modelMapper
        .map(serviceModel, OrganisationBindingModel.class);

    bindingModel.setCountry(serviceModel.getCountry().getNiceName());
    return bindingModel;
  }

  @PostMapping("/view/change-picture")
  @PreAuthorize("isAuthenticated() AND hasRole('ROLE_CORP')")
  public ModelAndView changeProfilePicture(Principal principal,
                                           @RequestParam("file") MultipartFile file) throws IOException {

    ImageServiceModel imageServiceModel = this.imageService.saveInDb(file);

    this.organisationService.editOrganisationPicture(principal.getName(), imageServiceModel);
    return super.redirect("/organisation/view");
  }
}
