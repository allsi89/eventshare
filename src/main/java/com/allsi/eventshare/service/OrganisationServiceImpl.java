package com.allsi.eventshare.service;

import com.allsi.eventshare.domain.entities.Country;
import com.allsi.eventshare.domain.entities.Organisation;
import com.allsi.eventshare.domain.entities.User;
import com.allsi.eventshare.domain.models.service.OrganisationServiceModel;
import com.allsi.eventshare.domain.models.service.UserServiceModel;
import com.allsi.eventshare.repository.OrganisationRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrganisationServiceImpl implements OrganisationService {
  private static final String ORG_NOT_FOUND = "Organisation not found";

  private final OrganisationRepository organisationRepository;
  private final UserService userService;
  private final ModelMapper modelMapper;

  @Autowired
  public OrganisationServiceImpl(OrganisationRepository organisationRepository, UserService userService, ModelMapper modelMapper) {
    this.organisationRepository = organisationRepository;
    this.userService = userService;
    this.modelMapper = modelMapper;
  }


  @Override
  public OrganisationServiceModel getOrganisationByUsername(String username) {
    UserServiceModel serviceModel = this.userService.findUserByUsername(username);
    Organisation organisation =  this.organisationRepository.findByUser_Id(serviceModel.getId())
        .orElseThrow(() -> new IllegalArgumentException(ORG_NOT_FOUND));

    return this.modelMapper.map(organisation, OrganisationServiceModel.class);
  }

  @Override
  public void addOrganisation(OrganisationServiceModel serviceModel, String name, String countryId) {
    UserServiceModel user = this.userService.findUserByUsername(name);

    if (serviceModel.getEmail()== null){
      serviceModel.setEmail(user.getEmail());
    }

    Organisation organisation = this.modelMapper
        .map(serviceModel, Organisation.class);

    organisation.setCountry(new Country());
    organisation.getCountry().setId(countryId);

    organisation.setUser(new User());
    organisation.getUser().setId(user.getId());

    this.organisationRepository.saveAndFlush(organisation);
    this.userService.addCorpToUserRoles(name);
  }

  @Override
  public void deleteOrganisation(String username) {
    UserServiceModel serviceModel = this.userService.findUserByUsername(username);

    Organisation organisation =  this.organisationRepository
        .findByUser_Id(serviceModel.getId())
        .orElseThrow(() -> new IllegalArgumentException(ORG_NOT_FOUND));

    this.userService.serCorpUserInactive(username);

    this.organisationRepository.delete(organisation);
  }

  @Override
  public void editOrganisation(OrganisationServiceModel organisationServiceModel, String username, String countryId) {
    UserServiceModel userServiceModel = this.userService.findUserByUsername(username);

    Organisation organisationToBeSaved = this.organisationRepository
        .findByUser_Id(userServiceModel.getId())
        .orElseThrow(() -> new IllegalArgumentException(ORG_NOT_FOUND));

    organisationServiceModel.setId(organisationToBeSaved.getId());

    Organisation organisation = this.modelMapper.map(organisationServiceModel, Organisation.class);

    organisation.setCountry(new Country());
    organisation.getCountry().setId(countryId);

    this.organisationRepository.saveAndFlush(organisation);

  }

}
