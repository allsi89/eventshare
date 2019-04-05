package com.allsi.eventshare.service;

import com.allsi.eventshare.domain.models.service.RoleServiceModel;

import java.util.List;

public interface RoleService {
  void seedRolesInDb();

  RoleServiceModel findByAuthority(String authority);

  List<RoleServiceModel> getAllRoles();

  List<RoleServiceModel> getAllRolesNotCorp();
}
