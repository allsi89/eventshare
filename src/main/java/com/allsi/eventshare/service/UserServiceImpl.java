package com.allsi.eventshare.service;

import com.allsi.eventshare.domain.entities.Role;
import com.allsi.eventshare.domain.entities.User;
import com.allsi.eventshare.domain.models.service.UserServiceModel;
import com.allsi.eventshare.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

import static com.allsi.eventshare.constants.Constants.*;

@Service
public class UserServiceImpl implements UserService {
  private static final String USER_NOT_FOUND_ERR = "Username not found.";

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

    User user = this.modelMapper.map(serviceModel, User.class);
    user.setPassword(this.encoder.encode(serviceModel.getPassword()));
    user.setAccountNonExpired(true);
    user.setAccountNonLocked(true);
    user.setCredentialsNonExpired(true);
    user.setEnabled(true);
    user.setCorporate(false);
    this.assignRolesToUser(user);

    try {
      this.userRepository.saveAndFlush(user);
      return true;
    } catch (Exception e){
      e.printStackTrace();
      return false;
    }
  }

  private void assignRolesToUser(User user) {
    if (this.userRepository.count() == 0){
      user.setRoles(this.roleService.getAllRoles()
      .stream()
      .map(r-> this.modelMapper.map(r, Role.class))
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
