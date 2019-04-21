package com.allsi.eventshare.validation.organisation;

import com.allsi.eventshare.domain.entities.User;
import com.allsi.eventshare.domain.models.binding.OrganisationBindingModel;
import com.allsi.eventshare.repository.OrganisationRepository;
import com.allsi.eventshare.repository.UserRepository;
import com.allsi.eventshare.validation.ValidationConstants;
import com.allsi.eventshare.validation.annotation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.Errors;

@Validator
public class OrganisationAddValidator implements org.springframework.validation.Validator {
  private final OrganisationRepository organisationRepository;
  private final UserRepository userRepository;

  @Autowired
  public OrganisationAddValidator(OrganisationRepository organisationRepository, UserRepository userRepository) {
    this.organisationRepository = organisationRepository;
    this.userRepository = userRepository;
  }

  @Override
  public boolean supports(Class<?> aClass) {
    return OrganisationBindingModel.class.equals(aClass);
  }

  @Override
  public void validate(Object o, Errors errors) {

    OrganisationBindingModel bindingModel = (OrganisationBindingModel) o;

    if (bindingModel.getEmail() == null) {
      return;
    }

    User user = this.userRepository.findByEmail(bindingModel.getEmail())
        .orElse(null);

    String username = SecurityContextHolder.getContext()
        .getAuthentication().getName();

    if (user != null && !user.getUsername().equals(username)) {
      errors.rejectValue(
          "email",
          ValidationConstants.EMAIL_ALREADY_TAKEN,
          ValidationConstants.EMAIL_ALREADY_TAKEN
      );
    }

    this.organisationRepository
        .findByEmail(bindingModel.getEmail()).ifPresent(existing -> errors.rejectValue(
        "email",
        ValidationConstants.EMAIL_ALREADY_EXISTS,
        ValidationConstants.EMAIL_ALREADY_EXISTS
    ));

  }
}
