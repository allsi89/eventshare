package com.allsi.eventshare.service;

import com.allsi.eventshare.domain.models.service.ImageServiceModel;
import com.allsi.eventshare.domain.models.service.UserServiceModel;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface UserService extends UserDetailsService {
  boolean register(UserServiceModel serviceModel);

  UserServiceModel findUserByUsername(String name);

  void editUserProfile(UserServiceModel userServiceModel, ImageServiceModel imageServiceModel);

  void editUserPassword(UserServiceModel userModel, String name, String oldPassword);

  void addCorpToUserRoles(String name);

  void setCorpUserInactive(String username);

  void editUserPicture(String username, ImageServiceModel imageServiceModel) throws IOException;
}
