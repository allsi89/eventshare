package com.allsi.eventshare.validation.user;

import com.allsi.eventshare.domain.entities.User;
import com.allsi.eventshare.domain.models.binding.UserEditPasswordBindingModel;
import com.allsi.eventshare.repository.UserRepository;
import com.allsi.eventshare.validation.ValidationConstants;
import com.allsi.eventshare.validation.annotation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.Errors;

@Validator
public class UserChangePasswordValidator implements org.springframework.validation.Validator {
  private final UserRepository userRepository;
  private final BCryptPasswordEncoder encoder;

  @Autowired
  public UserChangePasswordValidator(UserRepository userRepository, BCryptPasswordEncoder encoder) {
    this.userRepository = userRepository;
    this.encoder = encoder;
  }

  @Override
  public boolean supports(Class<?> aClass) {
    return UserEditPasswordBindingModel.class.equals(aClass);
  }

  @Override
  public void validate(Object o, Errors errors) {

    UserEditPasswordBindingModel bindingModel = (UserEditPasswordBindingModel) o;

    User user = this.userRepository.findByUsername(bindingModel.getUsername())
        .orElse(null);

    if (user == null){
      return;
    }

    if (!this.encoder.matches(bindingModel.getOldPassword(), user.getPassword())) {
      errors.rejectValue(
          "oldPassword",
          ValidationConstants.WRONG_PASSWORD,
          ValidationConstants.WRONG_PASSWORD
      );
    }

    if (bindingModel.getPassword() != null
        && !bindingModel.getPassword().equals(bindingModel.getConfirmPassword())) {
      errors.rejectValue(
          "password",
          ValidationConstants.PASSWORDS_DO_NOT_MATCH,
          ValidationConstants.PASSWORDS_DO_NOT_MATCH
      );
    }

  }
}
