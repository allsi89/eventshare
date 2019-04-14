package com.allsi.eventshare.service;

import com.allsi.eventshare.domain.models.service.ImageServiceModel;
import com.allsi.eventshare.domain.models.service.UserServiceModel;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

public interface UserService extends UserDetailsService {
  void register(UserServiceModel serviceModel);

  UserServiceModel findUserByUsername(String name);

  void editUserProfile(UserServiceModel userServiceModel);

  void editUserPassword(UserServiceModel userModel, String name, String oldPassword);

  void editUserPicture(String username, ImageServiceModel imageServiceModel) throws IOException;

  List<UserServiceModel> findAllUsers(String username);

  UserServiceModel findUserById(String id);
}
