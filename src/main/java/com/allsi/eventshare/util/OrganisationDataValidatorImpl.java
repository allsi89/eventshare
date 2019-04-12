package com.allsi.eventshare.util;

import com.allsi.eventshare.domain.entities.Organisation;
import com.allsi.eventshare.domain.entities.User;
import com.allsi.eventshare.repository.OrganisationRepository;
import com.allsi.eventshare.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrganisationDataValidatorImpl implements OrganisationDataValidator {

  private static final String ORG_NAME_IN_USE = "This name is already taken!";
  private static final String ORG_EMAIL_IN_USE = "This email is used by another organisation! " +
      "Please, provide a valid email.";

  private final OrganisationRepository organisationRepository;
  private final UserRepository userRepository;

  @Autowired
  public OrganisationDataValidatorImpl(OrganisationRepository organisationRepository, UserRepository userRepository) {
    this.organisationRepository = organisationRepository;
    this.userRepository = userRepository;
  }

  @Override
  public void validateNameNotTaken(String originalName, String newName) {
    if (originalName != null && originalName.equals(newName)){
      return;
    }

    Organisation organisation = this.organisationRepository
        .findByName(newName)
        .orElse(null);

    if (organisation != null)
      throw new IllegalArgumentException(ORG_NAME_IN_USE);
  }

  @Override
  public void validateEmailNotTaken(String originalEmail, String newEmail) {
    if (originalEmail != null && originalEmail.equals(newEmail)){
      return;
    }

    Organisation organisation = this.organisationRepository
        .findByEmail(newEmail)
        .orElse(null);

    if (organisation != null)
      throw new IllegalArgumentException(ORG_EMAIL_IN_USE);
  }

  @Override
  public void validateEmailNotMatchUserEmail(String requesterUsername, String newEmail) {
    User user = this.userRepository.findByEmail(newEmail)
        .orElse(null);

    if (user == null) {
      return;
    }

    if (user.getUsername().equals(requesterUsername)){
      return;
    }

    throw new IllegalArgumentException(ORG_EMAIL_IN_USE);
  }

}
