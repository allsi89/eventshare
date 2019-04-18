package com.allsi.eventshare.validation.event;

import com.allsi.eventshare.domain.models.binding.AddEventBindingModel;
import com.allsi.eventshare.validation.ValidationConstants;
import com.allsi.eventshare.validation.annotation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;

@Validator
public class AddEventValidator implements org.springframework.validation.Validator {
  private final EventValidatorHelper eventValidatorHelper;

  @Autowired
  public AddEventValidator(EventValidatorHelper eventValidatorHelper) {
    this.eventValidatorHelper = eventValidatorHelper;
  }

  @Override
  public boolean supports(Class<?> aClass) {
    return AddEventBindingModel.class.equals(aClass);
  }

  @Override
  public void validate(Object o, Errors errors) {
    AddEventBindingModel bindingModel = (AddEventBindingModel) o;

    boolean isValid = this.eventValidatorHelper
        .validateDate(bindingModel.getStartsOnDate(), bindingModel.getStartsOnTime());

    if (!isValid){
      errors.rejectValue(
          "startsOnDate",
          ValidationConstants.WRONG_DATE,
          ValidationConstants.WRONG_DATE
      );
    }
  }
}
