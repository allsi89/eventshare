package com.allsi.eventshare.util;

public interface UserDataValidator {

  void validateEmailNotTakenOnRegister(String email);

  void validateUsernameNotTaken(String username);

  void validateEmailNotTakenOnEdit(String requesterUsername, String email);
}
