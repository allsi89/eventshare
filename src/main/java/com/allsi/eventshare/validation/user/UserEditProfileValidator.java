package com.allsi.eventshare.validation.user;

import com.allsi.eventshare.domain.entities.Organisation;
import com.allsi.eventshare.domain.entities.User;
import com.allsi.eventshare.domain.models.binding.UserEditBindingModel;
import com.allsi.eventshare.repository.OrganisationRepository;
import com.allsi.eventshare.repository.UserRepository;
import com.allsi.eventshare.validation.ValidationConstants;
import com.allsi.eventshare.validation.annotation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;

@Validator
public class UserEditProfileValidator implements org.springframework.validation.Validator {
  private final UserRepository userRepository;
  private final OrganisationRepository organisationRepository;

  @Autowired
  public UserEditProfileValidator(UserRepository userRepository, OrganisationRepository organisationRepository) {
    this.userRepository = userRepository;
    this.organisationRepository = organisationRepository;
  }

  @Override
  public boolean supports(Class<?> aClass) {
    return UserEditBindingModel.class.equals(aClass);
  }

  @Override
  public void validate(Object o, Errors errors) {

    UserEditBindingModel userEditBindingModelModel = (UserEditBindingModel) o;

    User existing = this.userRepository
        .findByEmail(userEditBindingModelModel.getEmail())
        .orElse(null);

    Organisation organisation = this.organisationRepository
        .findByEmail(userEditBindingModelModel.getEmail())
        .orElse(null);

    if (
        (existing != null &&
            !existing.getUsername().equals(userEditBindingModelModel.getUsername()))

            || (organisation != null &&
            !organisation.getUser().getUsername()
                .equals(userEditBindingModelModel.getUsername()))
    ) {
      errors.rejectValue(
          "email",
          ValidationConstants.EMAIL_ALREADY_EXISTS,
          ValidationConstants.EMAIL_ALREADY_EXISTS
      );
    }
  }
}
