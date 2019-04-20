package com.allsi.eventshare.service.organisation;

import com.allsi.eventshare.domain.models.service.ImageServiceModel;
import com.allsi.eventshare.domain.models.service.OrganisationServiceModel;

import java.io.IOException;
import java.util.List;

public interface OrganisationService {

  OrganisationServiceModel getOrganisationByUsername(String username);

  boolean addOrganisation(OrganisationServiceModel serviceModel, String name, String countryId);

  boolean deleteOrganisation(String name);

  void editOrganisation(OrganisationServiceModel serviceModel, String username, String countryId);

  void editOrganisationPicture(String name, ImageServiceModel imageServiceModel) throws IOException;

  List<OrganisationServiceModel> findAllOrganisationsWithEvents();

  OrganisationServiceModel findById(String id);
}
