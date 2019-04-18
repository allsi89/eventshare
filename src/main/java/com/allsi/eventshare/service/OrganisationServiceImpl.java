package com.allsi.eventshare.service;

import com.allsi.eventshare.domain.entities.*;
import com.allsi.eventshare.domain.models.service.ImageServiceModel;
import com.allsi.eventshare.domain.models.service.OrganisationServiceModel;
import com.allsi.eventshare.errors.AuthorisationNotFoundException;
import com.allsi.eventshare.errors.CountryNotFoundException;
import com.allsi.eventshare.errors.IllegalOperationException;
import com.allsi.eventshare.errors.OrganisationNotFoundException;
import com.allsi.eventshare.repository.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static com.allsi.eventshare.common.GlobalConstants.CORP;
import static com.allsi.eventshare.common.GlobalConstants.USER_NOT_FOUND_ERR;

@Service
public class OrganisationServiceImpl implements OrganisationService {
  private final OrganisationRepository organisationRepository;
  private final UserRepository userRepository;
  private final RoleRepository roleRepository;
  private final CountryRepository countryRepository;
  private final EventRepository eventRepository;
  private final ModelMapper modelMapper;

  @Autowired
  public OrganisationServiceImpl(OrganisationRepository organisationRepository, UserRepository userRepository, RoleRepository roleRepository, CountryRepository countryRepository, EventRepository eventRepository, ModelMapper modelMapper) {
    this.organisationRepository = organisationRepository;
    this.userRepository = userRepository;
    this.roleRepository = roleRepository;
    this.countryRepository = countryRepository;
    this.eventRepository = eventRepository;
    this.modelMapper = modelMapper;
  }

  @Override
  public OrganisationServiceModel getOrganisationByUsername(String username) {
    Organisation organisation = this.findByUsername(username);

    return this.modelMapper
        .map(organisation, OrganisationServiceModel.class);
  }

  @Override
  public void addOrganisation(OrganisationServiceModel serviceModel, String username, String countryId) {
    User user = this.findByUserName(username);

    if (serviceModel.getEmail() == null) {
      serviceModel.setEmail(user.getEmail());
    }

    Organisation organisation = this.modelMapper
        .map(serviceModel, Organisation.class);

    organisation.setCountry(this.countryRepository.findById(countryId)
        .orElseThrow(CountryNotFoundException::new));

    Role role = this.roleRepository.findByAuthority(CORP)
        .orElseThrow(AuthorisationNotFoundException::new);

    user.getRoles().add(role);

    organisation.setUser(user);

    this.organisationRepository.save(organisation);
  }

  @Override
  public void deleteOrganisation(String username) {
    Organisation organisation = this.findByUsername(username);

    User user = this.findByUserName(username);

    user.setRoles(user.getRoles()
        .stream()
        .filter(r -> !r.getAuthority().equals(CORP)).collect(Collectors.toSet()));

    this.organisationRepository.delete(organisation);
  }

  @Override
  public void editOrganisation(OrganisationServiceModel organisationServiceModel, String username, String countryId) {

    //todo - possibly doesn't set email right if no such is provided
    Organisation original = this.findByUsername(username);

    Organisation organisation = this.modelMapper
        .map(organisationServiceModel, Organisation.class);

    organisation.setCountry(this.countryRepository.findById(countryId)
        .orElseThrow(CountryNotFoundException::new));

    organisation.setUser(original.getUser());

    organisation.setId(original.getId());

    organisation.setImage(original.getImage());

    this.organisationRepository.save(organisation);
  }

  @Override
  public void editOrganisationPicture(String username, ImageServiceModel imageServiceModel) {
    Organisation organisation = this.findByUsername(username);

    Image image = this.modelMapper.map(imageServiceModel, Image.class);

    organisation.setImage(image);

    this.organisationRepository.save(organisation);
  }

  @Override
  public List<OrganisationServiceModel> findAllOrganisationsWithEvents(String username) {
    List<Event> events = this.eventRepository
        .findAllGroupByCreator();

    List<OrganisationServiceModel> organisations = new ArrayList<>();

    for (Event event : events) {
      this.organisationRepository
          .findByUser_Id(event.getCreator().getId())
          .ifPresent(organisation ->
              organisations
                  .add(this.modelMapper
                      .map(organisation, OrganisationServiceModel.class)));

    }

    organisations
        .sort(Comparator.comparing(OrganisationServiceModel::getName));

    return organisations;
  }

  @Override
  public OrganisationServiceModel findById(String id) {
    Organisation organisation = this.organisationRepository
        .findById(id)
        .orElseThrow(OrganisationNotFoundException::new);

    return this.modelMapper
        .map(organisation, OrganisationServiceModel.class);
  }


  private User findByUserName(String username) {
    return this.userRepository.findByUsername(username)
        .orElseThrow(() -> new UsernameNotFoundException(USER_NOT_FOUND_ERR));
  }

  private Organisation findByUsername(String username) {
    return this.organisationRepository.findByUser_Username(username)
        .orElseThrow(OrganisationNotFoundException::new);
  }

}
