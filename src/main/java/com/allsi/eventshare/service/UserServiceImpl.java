package com.allsi.eventshare.service;

import com.allsi.eventshare.domain.entities.BaseEntity;
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

import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.allsi.eventshare.constants.Constants.*;

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
  public boolean register(UserServiceModel serviceModel) {
    this.roleService.seedRolesInDb();
    serviceModel.setPassword(this.encoder.encode(serviceModel.getPassword()));

    User user = this.modelMapper
        .map(serviceModel, User.class);

    this.assignRolesToUser(user);

    try {
      this.userRepository.saveAndFlush(user);
      return true;
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
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
  public void editUserProfile(UserServiceModel userServiceModel) {
    User user = this.userRepository.findByUsername(userServiceModel.getUsername())
        .orElseThrow(() -> new UsernameNotFoundException(USER_NOT_FOUND_ERR));

    user.setEmail(userServiceModel.getEmail());
    user.setAbout(userServiceModel.getAbout());

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

    toEdit.setRoles(user.getRoles());

    this.userRepository.saveAndFlush(toEdit);
  }

  @Override
  public void setCorpUserInactive(String username) {
    User user = this.userRepository.findByUsername(username)
        .orElseThrow(() -> new UsernameNotFoundException(USER_NOT_FOUND_ERR));

    RoleServiceModel roleServiceModel = this.roleService.findByAuthority(CORP);

    Set<Role> roles = user.getRoles()
        .stream()
        .filter(role -> !role.getAuthority().equals(CORP))
        .collect(Collectors.toSet());

    user.setRoles(roles);

    this.userRepository.saveAndFlush(user);
  }

  @Override
  public void editUserPicture(String username, ImageServiceModel imageServiceModel) throws IOException {
    User user = this.userRepository.findByUsername(username)
        .orElseThrow(() -> new UsernameNotFoundException(USER_NOT_FOUND_ERR));

    Image image = this.modelMapper.map(imageServiceModel, Image.class);

    user.setImage(image);

    this.userRepository.saveAndFlush(user);
  }

  @Override
  public List<String> findUserAttendingEvents(String username) {
    User user = this.userRepository.findByUsername(username)
        .orElseThrow(() -> new UsernameNotFoundException(USER_NOT_FOUND_ERR));

    return user.getAttendanceEvents()
        .stream()
        .map(BaseEntity::getId)
        .collect(Collectors.toList());
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


  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    return this.userRepository.findByUsername(username)
        .orElseThrow(() -> new UsernameNotFoundException(USER_NOT_FOUND_ERR));
  }
}
