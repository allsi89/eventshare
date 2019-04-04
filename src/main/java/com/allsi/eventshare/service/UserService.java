package com.allsi.eventshare.service;

import com.allsi.eventshare.domain.models.binding.UserEditPasswordBindingModel;
import com.allsi.eventshare.domain.models.service.ImageServiceModel;
import com.allsi.eventshare.domain.models.service.UserServiceModel;
import com.allsi.eventshare.domain.models.view.OrganisationViewModel;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
  boolean register(UserServiceModel serviceModel);

  UserServiceModel findUserByUsername(String name);

  OrganisationViewModel findUserOrganisation(String name);

  void editUserProfile(UserServiceModel userServiceModel);

  void editUserImage(UserServiceModel userServiceModel, ImageServiceModel imageServiceModel);

  void editUserPassword(UserServiceModel userModel, String name, String oldPassword);
}
