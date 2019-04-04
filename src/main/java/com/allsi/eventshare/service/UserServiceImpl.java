package com.allsi.eventshare.service;

import com.allsi.eventshare.domain.entities.Image;
import com.allsi.eventshare.domain.entities.Role;
import com.allsi.eventshare.domain.entities.User;
import com.allsi.eventshare.domain.models.service.ImageServiceModel;
import com.allsi.eventshare.domain.models.service.OrganisationServiceModel;
import com.allsi.eventshare.domain.models.service.UserServiceModel;
import com.allsi.eventshare.domain.models.view.OrganisationViewModel;
import com.allsi.eventshare.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

import static com.allsi.eventshare.constants.Constants.USER;

@Service
public class UserServiceImpl implements UserService {
  private static final String USER_NOT_FOUND_ERR = "User not found!";
  private static final String INCORRECT_PASSWORD = "Incorrect password!";

  private final UserRepository userRepository;
  private final RoleService roleService;
  private final ImageService imageService;
  private final ModelMapper modelMapper;
  private final BCryptPasswordEncoder encoder;

  @Autowired
  public UserServiceImpl(UserRepository userRepository, RoleService roleService, ImageService imageService, ModelMapper modelMapper, BCryptPasswordEncoder encoder) {
    this.userRepository = userRepository;
    this.roleService = roleService;
    this.imageService = imageService;
    this.modelMapper = modelMapper;
    this.encoder = encoder;
  }

  @Override
  public boolean register(UserServiceModel serviceModel) {
    this.roleService.seedRolesInDb();

    User user = this.modelMapper
        .map(this.setValuesToUserFields(serviceModel), User.class);

    this.assignRolesToUser(user);

    if (user != null) {
      this.userRepository.saveAndFlush(user);
      return true;
    }
    return false;
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

  @Override
  public OrganisationViewModel findUserOrganisation(String name) {
    UserServiceModel user = this.findUserByUsername(name);

    OrganisationServiceModel organisationServiceModel = this.modelMapper
        .map(user.getOrganisation(), OrganisationServiceModel.class);
    return getOrganisationViewModel(organisationServiceModel);
  }

  @Override
  public void editUserProfile(UserServiceModel userServiceModel) {
    User user = this.userRepository.findByUsername(userServiceModel.getUsername())
        .orElseThrow(() -> new UsernameNotFoundException(USER_NOT_FOUND_ERR));

    user.setEmail(userServiceModel.getEmail());
    this.userRepository.saveAndFlush(user);
  }

  @Override
  public void editUserImage(UserServiceModel userServiceModel, ImageServiceModel imageServiceModel) {
    User user = this.userRepository.findByUsername(userServiceModel.getUsername())
        .orElseThrow(() -> new UsernameNotFoundException(USER_NOT_FOUND_ERR));
    Image image = this.imageService.findImageById(imageServiceModel.getId());

    user.setImage(image);
    this.userRepository.saveAndFlush(user);

  }

  @Override
  public void editUserPassword(UserServiceModel model, String name, String oldPassword) {
    User user = this.userRepository.findByUsername(name)
        .orElseThrow(() -> new UsernameNotFoundException(USER_NOT_FOUND_ERR));

    if (!this.encoder.matches(oldPassword, user.getPassword())){
      throw new IllegalArgumentException(INCORRECT_PASSWORD);
    }

    user.setPassword(this.encoder.encode(model.getPassword()));

    this.userRepository.saveAndFlush(user);
  }


  private OrganisationViewModel getOrganisationViewModel(OrganisationServiceModel organisationServiceModel) {
    OrganisationViewModel organisationViewModel = this.modelMapper
        .map(organisationServiceModel, OrganisationViewModel.class);

    organisationViewModel.setCountry(organisationServiceModel.getCountry().getNiceName());
    organisationViewModel.setCity(organisationServiceModel.getCity().getName());
    organisationViewModel.setCityPostCode(organisationServiceModel.getCity().getPostCode());
    organisationViewModel.setPhone(organisationServiceModel
        .getCountry().getPhoneCode() + organisationServiceModel.getPhone());

    return organisationViewModel;
  }

  private void assignRolesToUser(User user) {
    if (this.userRepository.count() == 0) {
      user.setRoles(this.roleService.getAllRoles()
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
