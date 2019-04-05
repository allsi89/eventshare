package com.allsi.eventshare.web.controllers;

import com.allsi.eventshare.service.CloudService;
import com.allsi.eventshare.service.OrganisationService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

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
}
