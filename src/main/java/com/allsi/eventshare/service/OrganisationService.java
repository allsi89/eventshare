package com.allsi.eventshare.service;

import com.allsi.eventshare.domain.models.service.OrganisationServiceModel;

public interface OrganisationService {
  OrganisationServiceModel getOrganisationByUsername(String username);

  boolean addOrganisation(OrganisationServiceModel serviceModel, String name, String countryId);

  void deleteOrganisation(String name);
}
