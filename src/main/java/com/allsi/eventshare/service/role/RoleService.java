package com.allsi.eventshare.service.role;

import com.allsi.eventshare.domain.models.service.RoleServiceModel;

import java.util.List;

public interface RoleService {
  void seedRolesInDb();

  RoleServiceModel findByAuthority(String authority);

  List<RoleServiceModel> getAllRolesNotCorp();
}
