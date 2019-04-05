package com.allsi.eventshare.service;

import com.allsi.eventshare.domain.models.service.OrganisationServiceModel;

public interface OrganisationService {
  OrganisationServiceModel getOrganisationById(String username);

  boolean addOrganisation(OrganisationServiceModel serviceModel, String name);
}
