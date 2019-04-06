package com.allsi.eventshare.service;

import com.allsi.eventshare.domain.entities.Role;
import com.allsi.eventshare.domain.entities.User;
import com.allsi.eventshare.domain.models.service.UserServiceModel;
import com.allsi.eventshare.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.allsi.eventshare.constants.Constants.CORP;
import static com.allsi.eventshare.constants.Constants.USER;

@Service
public class UserServiceImpl implements UserService {
  private static final String USER_NOT_FOUND_ERR = "User not found!";
  private static final String INCORRECT_PASSWORD = "Incorrect password!";

  private final UserRepository userRepository;
  private final RoleService roleService;
  private final ModelMapper modelMapper;
  private final BCryptPasswordEncoder encoder;

  @Autowired
  public UserServiceImpl(UserRepository userRepository, RoleService roleService, ModelMapper modelMapper, BCryptPasswordEncoder encoder) {
    this.userRepository = userRepository;
    this.roleService = roleService;
    this.modelMapper = modelMapper;
    this.encoder = encoder;
  }

  @Override
  public boolean register(UserServiceModel serviceModel) {
    this.roleService.seedRolesInDb();

    User user = this.modelMapper
        .map(this.setValuesToUserFields(serviceModel), User.class);

    this.assignRolesToUser(user);

    try {
      this.userRepository.saveAndFlush(user);
      return true;
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
  }

  private UserServiceModel setValuesToUserFields(UserServiceModel serviceModel) {
    serviceModel.setPassword(this.encoder.encode(serviceModel.getPassword()));
    serviceModel.setAccountNonExpired(true);
    serviceModel.setAccountNonLocked(true);
    serviceModel.setCredentialsNonExpired(true);
    serviceModel.setEnabled(true);
    serviceModel.setCorporate(false);
    return serviceModel;
  }

  @Override
  public UserServiceModel findUserByUsername(String name) {
    User user = this.userRepository.findByUsername(name)
        .orElseThrow(() -> new UsernameNotFoundException(USER_NOT_FOUND_ERR));

    return this.modelMapper.map(user, UserServiceModel.class);
  }

//  @Override
//  public OrganisationViewModel findUserOrganisation(String name) {
//    UserServiceModel user = this.findUserByUsername(name);
//
//    OrganisationServiceModel organisationServiceModel = this.modelMapper
//        .map(user.getOrganisation(), OrganisationServiceModel.class);
//    return getOrganisationViewModel(organisationServiceModel);
//  }

  @Override
  public void editUserProfile(UserServiceModel userServiceModel) {
    User user = this.userRepository.findByUsername(userServiceModel.getUsername())
        .orElseThrow(() -> new UsernameNotFoundException(USER_NOT_FOUND_ERR));

    user.setEmail(userServiceModel.getEmail());
    if (userServiceModel.getImageUrl() != null) {
      user.setImageUrl(userServiceModel.getImageUrl());
    }
    this.userRepository.saveAndFlush(user);
  }

  @Override
  public void editUserPassword(UserServiceModel model, String name, String oldPassword) {
    User user = this.userRepository.findByUsername(name)
        .orElseThrow(() -> new UsernameNotFoundException(USER_NOT_FOUND_ERR));

    if (!this.encoder.matches(oldPassword, user.getPassword())) {
      throw new IllegalArgumentException(INCORRECT_PASSWORD);
    }

    user.setPassword(this.encoder.encode(model.getPassword()));

    this.userRepository.saveAndFlush(user);

  }

  @Override
  public void addCorpToUserRoles(String name) {
    UserServiceModel userServiceModel = this.findUserByUsername(name);

    userServiceModel.getRoles().add(this.roleService.findByAuthority(CORP));

    User user = this.modelMapper.map(userServiceModel, User.class);
    User toEdit = this.userRepository.findById(user.getId())
        .orElseThrow(() -> new UsernameNotFoundException(USER_NOT_FOUND_ERR));

    toEdit.setCorporate(true);
    toEdit.setRoles(user.getRoles());

    this.userRepository.saveAndFlush(toEdit);
  }

//  private OrganisationViewModel getOrganisationViewModel(OrganisationServiceModel organisationServiceModel) {
//    OrganisationViewModel organisationViewModel = this.modelMapper
//        .map(organisationServiceModel, OrganisationViewModel.class);
//
//    organisationViewModel.setCountry(organisationServiceModel.getCountry().getNiceName());
//    organisationViewModel.setCity(organisationServiceModel.getCity().getName());
//    organisationViewModel.setCityPostCode(organisationServiceModel.getCity().getPostCode());
//    organisationViewModel.setPhone(organisationServiceModel
//        .getCountry().getPhoneCode() + organisationServiceModel.getPhone());
//
//    return organisationViewModel;
//  }

  private void assignRolesToUser(User user) {
    if (this.userRepository.count() == 0) {
      user.setRoles(this.roleService.getAllRolesNotCorp()
          .stream()
          .map(r -> this.modelMapper.map(r, Role.class))
          .collect(Collectors.toSet()));
    } else {
      user.getRoles().add(this.modelMapper
          .map(this.roleService.findByAuthority(USER), Role.class));
    }
  }


  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    return this.userRepository.findByUsername(username)
        .orElseThrow(() -> new UsernameNotFoundException(USER_NOT_FOUND_ERR));
  }
}
