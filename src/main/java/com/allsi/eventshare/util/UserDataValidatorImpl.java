package com.allsi.eventshare.util;

import com.allsi.eventshare.domain.entities.Organisation;
import com.allsi.eventshare.domain.entities.User;
import com.allsi.eventshare.repository.OrganisationRepository;
import com.allsi.eventshare.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserDataValidatorImpl implements UserDataValidator{
  private static final String USER_EMAIL_TAKEN = "Email is already taken!";
  private static final String USERNAME_TAKEN = "This username is already taken!";
  private final UserRepository userRepository;
  private final OrganisationRepository organisationRepository;

  @Autowired
  public UserDataValidatorImpl(UserRepository userRepository, OrganisationRepository organisationRepository) {
    this.userRepository = userRepository;
    this.organisationRepository = organisationRepository;
  }

  @Override
  public void validateEmailNotTakenOnRegister(String email) {
    User user = this.userRepository.findByEmail(email)
        .orElse(null);

    if (user != null) {
      throw new IllegalArgumentException(USER_EMAIL_TAKEN);
    }

    Organisation organisation = this.organisationRepository.findByEmail(email)
        .orElse(null);

    if (organisation != null) {
      throw new IllegalArgumentException(USER_EMAIL_TAKEN);
    }

  }

  @Override
  public void validateUsernameNotTaken(String username) {
    User user = this.userRepository.findByUsername(username)
        .orElse(null);

    if (user != null) {
      throw new IllegalArgumentException(USERNAME_TAKEN);
    }
  }

  @Override
  public void validateEmailNotTakenOnEdit(String requesterUsername, String email) {
    User user = this.userRepository.findByEmail(email)
        .orElse(null);

    if (user != null && !user.getUsername().equals(requesterUsername)) {
      throw new IllegalArgumentException(USER_EMAIL_TAKEN);
    }

    Organisation organisation = this.organisationRepository.findByEmail(email)
        .orElse(null);

    if (organisation != null && !organisation.getUser().getUsername().equals(requesterUsername)) {
      throw new IllegalArgumentException(USER_EMAIL_TAKEN);
    }
  }
}
