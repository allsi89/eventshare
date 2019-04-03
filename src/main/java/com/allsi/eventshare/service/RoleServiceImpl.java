package com.allsi.eventshare.service;

import com.allsi.eventshare.domain.entities.Role;
import com.allsi.eventshare.domain.models.service.RoleServiceModel;
import com.allsi.eventshare.repository.RoleRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.allsi.eventshare.constants.Constants.*;

@Service
public class RoleServiceImpl implements RoleService {
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
      Role rootAdmin = new Role(ROOT_ADMIN);
      this.roleRepository.saveAndFlush(rootAdmin);
      Role admin = new Role(ADMIN);
      this.roleRepository.saveAndFlush(admin);
      Role moderator = new Role(MODERATOR);
      this.roleRepository.saveAndFlush(moderator);
      Role user = new Role(USER);
      this.roleRepository.saveAndFlush(user);
    }
  }

  @Override
  public RoleServiceModel findByAuthority(String authority) {

    return null;
  }

  @Override
  public List<RoleServiceModel> getAllRoles() {
    return this.roleRepository.findAll()
        .stream()
        .map(r->this.modelMapper.map(r, RoleServiceModel.class))
        .collect(Collectors.toList());
  }


}
