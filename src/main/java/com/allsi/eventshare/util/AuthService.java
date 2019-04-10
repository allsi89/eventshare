package com.allsi.eventshare.util;


public interface AuthService {
  void resetAuthCorp(boolean isToBeAdded);

  String getPrincipalUsername();
}
