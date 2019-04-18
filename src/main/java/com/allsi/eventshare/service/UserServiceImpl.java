package com.allsi.eventshare.service;

import com.allsi.eventshare.domain.entities.Image;
import com.allsi.eventshare.domain.entities.Role;
import com.allsi.eventshare.domain.entities.User;
import com.allsi.eventshare.domain.models.service.ImageServiceModel;
import com.allsi.eventshare.domain.models.service.RoleServiceModel;
import com.allsi.eventshare.domain.models.service.UserServiceModel;
import com.allsi.eventshare.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.allsi.eventshare.common.GlobalConstants.*;

@Service
public class UserServiceImpl implements UserService {
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
  public UserServiceModel findUserByUsername(String name) {
    User user = this.userRepository.findByUsername(name)
        .orElseThrow(() -> new UsernameNotFoundException(USER_NOT_FOUND_ERR));

    UserServiceModel userServiceModel = this.modelMapper.map(user, UserServiceModel.class);

    if (user.getImage() != null) {
      userServiceModel.setImageUrl(user.getImage().getUrl());
    }

    return userServiceModel;
  }

  @Override
  public void register(UserServiceModel serviceModel) {
    this.roleService.seedRolesInDb();

    serviceModel.setPassword(this.encoder.encode(serviceModel.getPassword()));

    User user = this.modelMapper.map(serviceModel, User.class);

    this.assignRolesToUser(user);
    this.userRepository.save(user);
  }

  @Override
  public void editUserProfile(UserServiceModel userServiceModel) {
    User user = this.userRepository.findByUsername(userServiceModel.getUsername())
        .orElseThrow(() -> new UsernameNotFoundException(USER_NOT_FOUND_ERR));

    user.setEmail(userServiceModel.getEmail());
    user.setAbout(userServiceModel.getAbout());

    this.userRepository.save(user);
  }

  @Override
  public void editUserPassword(UserServiceModel model, String name, String oldPassword) {
    User user = this.userRepository.findByUsername(name)
        .orElseThrow(() -> new UsernameNotFoundException(USER_NOT_FOUND_ERR));

    user.setPassword(this.encoder.encode(model.getPassword()));

    this.userRepository.save(user);
  }

  @Override
  public void editUserPicture(String username, ImageServiceModel imageServiceModel) {
    User user = this.userRepository.findByUsername(username)
        .orElseThrow(() -> new UsernameNotFoundException(USER_NOT_FOUND_ERR));

    Image image = this.modelMapper.map(imageServiceModel, Image.class);

    user.setImage(image);

    this.userRepository.save(user);
  }

  @Override
  public List<UserServiceModel> findAllUsersButRequester(String username) {
    return this.userRepository
        .findAll()
        .stream()
        .filter(u -> !u.getUsername().equals(username))
        .map(u -> this.modelMapper.map(u, UserServiceModel.class))
        .collect(Collectors.toList());
  }

  @Override
  public void updateRole(String id, String role) {
    User user = this.userRepository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("Incorrect id!"));

    UserServiceModel serviceModel = this.getServiceModel(user);

    switch (role) {
      case USER:
        serviceModel.getRoles().add(this.roleService.findByAuthority(USER));
        break;
      case MODERATOR:
        serviceModel.getRoles().add(this.roleService.findByAuthority(USER));
        serviceModel.getRoles().add(this.roleService.findByAuthority(MODERATOR));
        break;
      case ADMIN:
        serviceModel.getRoles().add(this.roleService.findByAuthority(USER));
        serviceModel.getRoles().add(this.roleService.findByAuthority(MODERATOR));
        serviceModel.getRoles().add(this.roleService.findByAuthority(ADMIN));
        break;
      default:
        break;
    }

    Set<Role> roles = serviceModel.getRoles()
        .stream()
        .map(r -> this.modelMapper.map(r, Role.class))
        .collect(Collectors.toSet());

    user.setRoles(roles);

    this.userRepository.saveAndFlush(user);
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    return this.userRepository.findByUsername(username)
        .orElseThrow(() -> new UsernameNotFoundException(USER_NOT_FOUND_ERR));
  }

  private UserServiceModel getServiceModel(User user) {

    Set<Role> roles = user.getRoles();

    boolean containsCorp = false;

    for (Role role : roles) {
      if (role.getAuthority().equals(CORP)) {
        containsCorp = true;
      }
    }

    UserServiceModel serviceModel = this.modelMapper
        .map(user, UserServiceModel.class);

    serviceModel.getRoles().clear();

    if (containsCorp) {
      RoleServiceModel role = this.roleService.findByAuthority(CORP);

      serviceModel.getRoles().add(role);
    }

    return serviceModel;
  }

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
}
