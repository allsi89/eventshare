package com.allsi.eventshare.validation.user;

import com.allsi.eventshare.domain.models.binding.UserRegisterBindingModel;
import com.allsi.eventshare.repository.OrganisationRepository;
import com.allsi.eventshare.repository.UserRepository;
import com.allsi.eventshare.validation.ValidationConstants;
import com.allsi.eventshare.validation.annotation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;

@Validator
public class UserRegisterValidator implements org.springframework.validation.Validator {
  private final UserRepository userRepository;
  private final OrganisationRepository organisationRepository;

  @Autowired
  public UserRegisterValidator(UserRepository userRepository, OrganisationRepository organisationRepository) {
    this.userRepository = userRepository;
    this.organisationRepository = organisationRepository;
  }

  @Override
  public boolean supports(Class<?> aClass) {
    return UserRegisterBindingModel.class.equals(aClass);
  }

  @Override
  public void validate(Object o, Errors errors) {

    UserRegisterBindingModel userRegisterBindingModel = (UserRegisterBindingModel) o;

    if (this.userRepository.findByUsername(userRegisterBindingModel.getUsername())
        .isPresent()) {
      errors.rejectValue(
          "username",
          ValidationConstants.USERNAME_ALREADY_EXISTS,
          ValidationConstants.USERNAME_ALREADY_EXISTS
      );
    }

    if (!userRegisterBindingModel.getPassword().equals(userRegisterBindingModel.getConfirmPassword())) {
      errors.rejectValue(
          "password",
          ValidationConstants.PASSWORDS_DO_NOT_MATCH,
          ValidationConstants.PASSWORDS_DO_NOT_MATCH
      );
    }

    if (this.userRepository.findByEmail(userRegisterBindingModel.getEmail()).isPresent()
        || this.organisationRepository.findByEmail(userRegisterBindingModel.getEmail()).isPresent()) {
      errors.rejectValue(
          "email",
          ValidationConstants.EMAIL_ALREADY_EXISTS,
          ValidationConstants.EMAIL_ALREADY_EXISTS
      );
    }
  }
}
