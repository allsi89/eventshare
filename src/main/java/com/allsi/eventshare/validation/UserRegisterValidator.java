package com.allsi.eventshare.validation;

import com.allsi.eventshare.domain.models.binding.UserRegisterBindingModel;
import com.allsi.eventshare.repository.OrganisationRepository;
import com.allsi.eventshare.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

//@Validator
public class UserRegisterValidator implements org.springframework.validation.Validator {
  private final UserRepository userRepository;
  private final OrganisationRepository organisationRepository;
  private final BCryptPasswordEncoder encoder;

  @Autowired
  public UserRegisterValidator(UserRepository userRepository, OrganisationRepository organisationRepository, BCryptPasswordEncoder encoder) {
    this.userRepository = userRepository;
    this.organisationRepository = organisationRepository;
    this.encoder = encoder;
  }

  @Override
  public boolean supports(Class<?> aClass) {
    return UserRegisterBindingModel.class.equals(aClass);
  }

  @Override
  public void validate(Object o, Errors errors) {

    UserRegisterBindingModel userRegisterBindingModel = (UserRegisterBindingModel) o;

    if (this.userRepository.findByUsername(userRegisterBindingModel.getUsername())
    .isPresent()){
      errors.rejectValue(
          "username",
          String.format(ValidationConstants.USERNAME_ALREDY_EXISTS,
              userRegisterBindingModel.getUsername()),
          String.format(ValidationConstants.USERNAME_ALREDY_EXISTS,
              userRegisterBindingModel.getUsername())
      );
    }

  }
}
