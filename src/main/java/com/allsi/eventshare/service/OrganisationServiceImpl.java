package com.allsi.eventshare.service;

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
  public OrganisationServiceModel getOrganisationById(String username) {
    UserServiceModel user = this.userService.findUserByUsername(username);
    Organisation organisation =  this.organisationRepository.findByUser_Id(user.getId())
        .orElseThrow(() -> new IllegalArgumentException(ORG_NOT_FOUND));

    return this.modelMapper.map(organisation, OrganisationServiceModel.class);
  }

  @Override
  public boolean addOrganisation(OrganisationServiceModel serviceModel, String name) {
    UserServiceModel user = this.userService.findUserByUsername(name);

    if (serviceModel.getEmail()== null){
      serviceModel.setEmail(user.getEmail());
    }

    Organisation organisation = this.modelMapper
        .map(serviceModel, Organisation.class);

    //This works!!!
    organisation.setUser(new User());
    organisation.getUser().setId(user.getId());

    //TODO -- city(createIfNotExist) and country -- set fields that won't be mapped too
    try {
      this.organisationRepository.saveAndFlush(organisation);
      this.userService.addCorpToUserRoles(name);
      return true;
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
  }
}
