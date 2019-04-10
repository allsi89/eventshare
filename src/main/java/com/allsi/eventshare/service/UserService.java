package com.allsi.eventshare.service;

import com.allsi.eventshare.domain.models.service.ImageServiceModel;
import com.allsi.eventshare.domain.models.service.UserServiceModel;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.io.IOException;
import java.util.List;

public interface UserService extends UserDetailsService {
  boolean register(UserServiceModel serviceModel);

  UserServiceModel findUserByUsername(String name);

  void editUserProfile(UserServiceModel userServiceModel);

  void editUserPassword(UserServiceModel userModel, String name, String oldPassword);

  void addCorpToUserRoles(String name);

  void setCorpUserInactive(String username);

  void editUserPicture(String username, ImageServiceModel imageServiceModel) throws IOException;

  List<String> findUserAttendingEvents(String principalUsername);

}
