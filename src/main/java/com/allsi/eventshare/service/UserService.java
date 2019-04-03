package com.allsi.eventshare.service;

import com.allsi.eventshare.domain.models.service.UserServiceModel;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
  boolean register(UserServiceModel serviceModel);
}
