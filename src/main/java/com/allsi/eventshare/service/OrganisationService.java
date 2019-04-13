package com.allsi.eventshare.service;

import com.allsi.eventshare.domain.models.service.ImageServiceModel;
import com.allsi.eventshare.domain.models.service.OrganisationServiceModel;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

public interface OrganisationService {
  OrganisationServiceModel getOrganisationByUsername(String username);

  void addOrganisation(OrganisationServiceModel serviceModel, String name, String countryId);

  void deleteOrganisation(String name);

  void editOrganisation(OrganisationServiceModel serviceModel, String username, String countryId);

  void editOrganisationPicture(String name, ImageServiceModel imageServiceModel) throws IOException;

  List<OrganisationServiceModel> findAllOrganisationsWithEvents();
}
