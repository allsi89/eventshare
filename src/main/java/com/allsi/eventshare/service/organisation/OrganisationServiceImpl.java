package com.allsi.eventshare.service.organisation;

import com.allsi.eventshare.domain.entities.*;
import com.allsi.eventshare.domain.models.service.ImageServiceModel;
import com.allsi.eventshare.domain.models.service.OrganisationServiceModel;
import com.allsi.eventshare.errors.CountryNotFoundException;
import com.allsi.eventshare.errors.OrganisationNotFoundException;
import com.allsi.eventshare.repository.*;
import com.allsi.eventshare.service.event.EventService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static com.allsi.eventshare.service.ServiceConstants.*;

@Service
public class OrganisationServiceImpl implements OrganisationService {
  private final OrganisationRepository organisationRepository;
  private final UserRepository userRepository;
  private final CountryRepository countryRepository;
  private final EventService eventService;

  private final ModelMapper modelMapper;

  @Autowired
  public OrganisationServiceImpl(OrganisationRepository organisationRepository, UserRepository userRepository, CountryRepository countryRepository, EventService eventService, ModelMapper modelMapper) {
    this.organisationRepository = organisationRepository;
    this.userRepository = userRepository;
    this.countryRepository = countryRepository;
    this.eventService = eventService;

    this.modelMapper = modelMapper;
  }

  @Override
  public OrganisationServiceModel getOrganisationByUsername(String username) {
    Organisation organisation = this.findByOrganisationByUsername(username);

    return this.modelMapper
        .map(organisation, OrganisationServiceModel.class);
  }

  @Override
  public boolean addOrganisation(OrganisationServiceModel serviceModel, String username, String countryId) {
    Organisation organisation = this.modelMapper
        .map(serviceModel, Organisation.class);

    organisation.setCountry(this.countryRepository.findById(countryId)
        .orElseThrow(() -> new CountryNotFoundException(COUNTRY_NOT_FOUND)));

    User user = this.findByUserName(username);

    if (serviceModel.getEmail() == null) {
      serviceModel.setEmail(user.getEmail());
    }

    organisation.setUser(user);

    this.organisationRepository.save(organisation);
    return true;
  }

  @Override
  public boolean deleteOrganisation(String username) {
    Organisation organisation = this.findByOrganisationByUsername(username);

    this.organisationRepository.deleteById(organisation.getId());

    return true;
  }

  @Override
  public void editOrganisation(OrganisationServiceModel organisationServiceModel, String username, String countryId) {

    Organisation original = this.findByOrganisationByUsername(username);

    Organisation organisation = this.modelMapper
        .map(organisationServiceModel, Organisation.class);

    organisation.setCountry(this.countryRepository.findById(countryId)
        .orElseThrow(() -> new CountryNotFoundException(COUNTRY_NOT_FOUND)));

    organisation.setUser(original.getUser());

    organisation.setId(original.getId());

    organisation.setImage(original.getImage());

    this.organisationRepository.save(organisation);
  }

  @Override
  public void editOrganisationPicture(String username, ImageServiceModel imageServiceModel) {
    Organisation organisation = this.findByOrganisationByUsername(username);

    Image image = this.modelMapper.map(imageServiceModel, Image.class);

    organisation.setImage(image);

    this.organisationRepository.save(organisation);
  }

  @Override
  public List<OrganisationServiceModel> findAllOrganisationsWithEvents() {
    List<String> creatorIds = this.eventService.findAllCreatorIds();

    List<OrganisationServiceModel> organisations = new ArrayList<>();

    for (String creatorId : creatorIds) {
      this.organisationRepository.findByUser_Id(creatorId)
          .ifPresent(organisation -> organisations.add(this.modelMapper.map(organisation, OrganisationServiceModel.class)));
    }

    organisations
        .sort(Comparator.comparing(OrganisationServiceModel::getName));

    return organisations;
  }

  @Override
  public OrganisationServiceModel findById(String id) {
    Organisation organisation = this.organisationRepository
        .findById(id)
        .orElseThrow(() -> new OrganisationNotFoundException(ORGANISATION_NOT_FOUND));

    return this.modelMapper
        .map(organisation, OrganisationServiceModel.class);
  }


  private User findByUserName(String username) {
    return this.userRepository.findByUsername(username)
        .orElseThrow(() -> new UsernameNotFoundException(USER_NOT_FOUND));
  }

  private Organisation findByOrganisationByUsername(String username) {
    return this.organisationRepository.findByUser_Username(username)
        .orElseThrow(() -> new OrganisationNotFoundException(ORGANISATION_NOT_FOUND));
  }

}
