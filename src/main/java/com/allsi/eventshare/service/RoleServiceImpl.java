package com.allsi.eventshare.service;

import com.allsi.eventshare.domain.entities.Role;
import com.allsi.eventshare.domain.models.service.RoleServiceModel;
import com.allsi.eventshare.repository.RoleRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.allsi.eventshare.constants.Constants.*;

@Service
public class RoleServiceImpl implements RoleService {
  private static final String ROLE_NOT_FOUND = "Role not found.";

  private final RoleRepository roleRepository;
  private final ModelMapper modelMapper;

  @Autowired
  public RoleServiceImpl(RoleRepository roleRepository, ModelMapper modelMapper) {
    this.roleRepository = roleRepository;
    this.modelMapper = modelMapper;
  }

  @Override
  public void seedRolesInDb() {
    if (this.roleRepository.count() == 0){

      List<String> authorities = new ArrayList<>(){{
        add(ROOT_ADMIN);
        add(ADMIN);
        add(MODERATOR);
        add(CORP);
        add(USER);
      }};

      for (String authority : authorities) {
        Role role = new Role(authority);
        this.roleRepository.save(role);
      }

    }

  }

  @Override
  public RoleServiceModel findByAuthority(String authority) {
    Role role = this.roleRepository.findByAuthority(authority)
        .orElseThrow(() -> new IllegalArgumentException(ROLE_NOT_FOUND));

    return this.modelMapper.map(role, RoleServiceModel.class);
  }

  @Override
  public List<RoleServiceModel> getAllRoles() {
    return this.roleRepository.findAll()
        .stream()
        .map(r->this.modelMapper.map(r, RoleServiceModel.class))
        .collect(Collectors.toList());
  }

  @Override
  public List<RoleServiceModel> getAllRolesNotCorp() {
    return this.roleRepository.findAllByAuthorityNot(CORP)
        .stream()
        .map(r->this.modelMapper.map(r, RoleServiceModel.class))
        .collect(Collectors.toList());
  }


}
