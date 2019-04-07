package com.allsi.eventshare.service;

import com.allsi.eventshare.domain.models.service.OrganisationServiceModel;

public interface OrganisationService {
  OrganisationServiceModel getOrganisationByUsername(String username);

  void addOrganisation(OrganisationServiceModel serviceModel, String name, String countryId);

  void deleteOrganisation(String name);

  void editOrganisation(OrganisationServiceModel serviceModel, String username, String countryId);
}
