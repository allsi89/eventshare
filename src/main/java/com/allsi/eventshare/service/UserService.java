package com.allsi.eventshare.service;

import com.allsi.eventshare.domain.models.service.UserServiceModel;
import com.allsi.eventshare.domain.models.view.OrganisationViewModel;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
  boolean register(UserServiceModel serviceModel);

  UserDetails findUserByUsername(String name);

  OrganisationViewModel findUserOrganisation(String name);

  boolean editUserProfile(UserServiceModel map, String oldPassword);
}
