package com.allsi.eventshare.validation.category;

import com.allsi.eventshare.domain.entities.Category;
import com.allsi.eventshare.domain.models.binding.CategoryBindingModel;
import com.allsi.eventshare.repository.CategoryRepository;
import com.allsi.eventshare.validation.ValidationConstants;
import com.allsi.eventshare.validation.annotation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;

@Validator
public class CategoryValidator implements org.springframework.validation.Validator {
  private final CategoryRepository categoryRepository;

  @Autowired
  public CategoryValidator(CategoryRepository categoryRepository) {
    this.categoryRepository = categoryRepository;
  }

  @Override
  public boolean supports(Class<?> aClass) {
    return CategoryBindingModel.class.equals(aClass);
  }
  @Override
  public void validate(Object o, Errors errors) {
    CategoryBindingModel bindingModel = (CategoryBindingModel) o;

    Category existing = this.categoryRepository.findByName(bindingModel.getName())
        .orElse(null);

    if (existing != null && !existing.getId().equals(bindingModel.getId()) ){
      errors.rejectValue("name",
          ValidationConstants.CATEGORY_ALREADY_EXIST,
          ValidationConstants.CATEGORY_ALREADY_EXIST
      );
    }
  }
}
