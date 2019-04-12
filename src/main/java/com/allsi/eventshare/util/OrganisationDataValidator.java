package com.allsi.eventshare.util;

public interface OrganisationDataValidator {
  void validateNameNotTaken(String originalName, String newName);

  void validateEmailNotTaken(String originalEmail, String newEmail);

  void validateEmailNotMatchUserEmail(String requesterUsername, String newEmail);


}
