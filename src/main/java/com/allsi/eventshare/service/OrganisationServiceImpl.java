package com.allsi.eventshare.service;

import com.allsi.eventshare.domain.entities.Image;
import com.allsi.eventshare.domain.entities.Organisation;
import com.allsi.eventshare.domain.entities.Role;
import com.allsi.eventshare.domain.entities.User;
import com.allsi.eventshare.domain.models.service.ImageServiceModel;
import com.allsi.eventshare.domain.models.service.OrganisationServiceModel;
import com.allsi.eventshare.repository.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Set;
import java.util.stream.Collectors;

import static com.allsi.eventshare.constants.Constants.*;

@Service
public class OrganisationServiceImpl implements OrganisationService {
  private static final String ORG_NOT_FOUND = "Organisation not found";
//  private static final String PLUS_CHAR = "+";

  private final OrganisationRepository organisationRepository;
  private final UserRepository userRepository;
  private final RoleRepository roleRepository;
  private final CountryRepository countryRepository;
  private final ModelMapper modelMapper;

  @Autowired
  public OrganisationServiceImpl(OrganisationRepository organisationRepository, UserRepository userRepository, RoleRepository roleRepository, CountryRepository countryRepository, ModelMapper modelMapper) {
    this.organisationRepository = organisationRepository;
    this.userRepository = userRepository;
    this.roleRepository = roleRepository;
    this.countryRepository = countryRepository;
    this.modelMapper = modelMapper;
  }


  @Override
  public OrganisationServiceModel getOrganisationByUsername(String username) {
    User user= this.userRepository.findByUsername(username)
        .orElseThrow(() -> new UsernameNotFoundException(USER_NOT_FOUND_ERR));

    Organisation organisation =  this.organisationRepository.findByUser_Id(user.getId())
        .orElseThrow(() -> new IllegalArgumentException(ORG_NOT_FOUND));

    OrganisationServiceModel organisationServiceModel = this.modelMapper
        .map(organisation, OrganisationServiceModel.class);

    organisationServiceModel.setPhone(organisation.getPhone());
    //TODO -- check image
//    if (organisation.getImage() != null){
//      organisationServiceModel.setImageUrl(organisation.getImage().getUrl());
//    }

    return organisationServiceModel;
  }

  @Override
  public void addOrganisation(OrganisationServiceModel serviceModel, String username, String countryId) {
    User user= this.userRepository.findByUsername(username)
        .orElseThrow(() -> new UsernameNotFoundException(USER_NOT_FOUND_ERR));

    if (serviceModel.getEmail()== null){
      serviceModel.setEmail(user.getEmail());
    }

    Organisation organisation = this.modelMapper
        .map(serviceModel, Organisation.class);

    organisation.setCountry(this.countryRepository.findById(countryId)
        .orElseThrow(() -> new IllegalArgumentException(COUNTRY_NOT_FOUND_ERR)));

    Role role = this.roleRepository.findByAuthority(CORP)
        .orElseThrow(()->new IllegalArgumentException(ROLE_NOT_FOUND_ERR));

    user.getRoles().add(role);

    organisation.setUser(user);

    this.organisationRepository.saveAndFlush(organisation);
  }

  @Override
  public void deleteOrganisation(String username) {
    User user= this.userRepository.findByUsername(username)
        .orElseThrow(() -> new UsernameNotFoundException(USER_NOT_FOUND_ERR));

    Organisation organisation =  this.organisationRepository
        .findByUser_Id(user.getId())
        .orElseThrow(() -> new IllegalArgumentException(ORG_NOT_FOUND));

    Set<Role> roles = user.getRoles();

    user.setRoles(roles
        .stream()
        .filter(r->!r.getAuthority().equals(CORP)).collect(Collectors.toSet()));

    this.organisationRepository.delete(organisation);
  }

  @Override
  public void editOrganisation(OrganisationServiceModel organisationServiceModel, String username, String countryId) {
    User user= this.userRepository.findByUsername(username)
        .orElseThrow(() -> new UsernameNotFoundException(USER_NOT_FOUND_ERR));

    Organisation organisation = this.modelMapper
        .map(organisationServiceModel, Organisation.class);

    organisation.setCountry(this.countryRepository.findById(countryId)
        .orElseThrow(() -> new IllegalArgumentException(COUNTRY_NOT_FOUND_ERR)));

    organisation.setUser(user);

    Organisation original = this.organisationRepository
        .findByUser_Id(user.getId())
        .orElseThrow(() -> new IllegalArgumentException(ORG_NOT_FOUND));

    organisation.setId(original.getId());

    organisation.setImage(original.getImage());

    this.organisationRepository.saveAndFlush(organisation);
  }

  @Override
  public void editOrganisationPicture(String username, ImageServiceModel imageServiceModel) throws IOException {
    User user= this.userRepository.findByUsername(username)
        .orElseThrow(() -> new UsernameNotFoundException(USER_NOT_FOUND_ERR));

    Organisation organisation = this.organisationRepository
        .findByUser_Id(user.getId())
        .orElseThrow(() -> new IllegalArgumentException(ORG_NOT_FOUND));

    Image image = this.modelMapper.map(imageServiceModel, Image.class);

    organisation.setImage(image);

    this.organisationRepository.saveAndFlush(organisation);
  }

}
