package com.allsi.eventshare.web.controllers;

import com.allsi.eventshare.domain.models.binding.CategoryBindingModel;
import com.allsi.eventshare.domain.models.service.CategoryServiceModel;
import com.allsi.eventshare.domain.models.view.CategoryViewModel;
import com.allsi.eventshare.service.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.stream.Collectors;

import static com.allsi.eventshare.constants.GlobalConstants.*;

@Controller
@RequestMapping("/categories")
public class CategoryController extends BaseController {
  private final CategoryService categoryService;
  private final ModelMapper modelMapper;

  @Autowired
  public CategoryController(CategoryService categoryService, ModelMapper modelMapper) {
    this.categoryService = categoryService;
    this.modelMapper = modelMapper;
  }

  @GetMapping("/all")
  @PreAuthorize("isAuthenticated() AND hasAnyRole('ROLE_ADMIN', 'ROLE_ROOT_ADMIN', 'ROLE_MODERATOR')")
  public ModelAndView allCategories(ModelAndView modelAndView) {
    List<CategoryViewModel> categories = this.categoryService.findAllCategories()
        .stream()
        .map(c -> this.modelMapper.map(c, CategoryViewModel.class))
        .collect(Collectors.toList());
    modelAndView.addObject("categories", categories);
    return super.view(ALL_CATEGORIES_VIEW, modelAndView);
  }

  @GetMapping("/add")
  @PreAuthorize("isAuthenticated() AND hasAnyRole('ROLE_ADMIN', 'ROLE_ROOT_ADMIN', 'ROLE_MODERATOR')")
  public ModelAndView addCategory(ModelAndView modelAndView,
                                  @ModelAttribute(name = "bindingModel")
                                      CategoryBindingModel bindingModel) {
    modelAndView.addObject("bindingModel", bindingModel);
    return super.view(ADD_CATEGORY_VIEW, modelAndView);
  }

  @PostMapping("/add")
  @PreAuthorize("isAuthenticated() AND hasAnyRole('ROLE_ADMIN', 'ROLE_ROOT_ADMIN', 'ROLE_MODERATOR')")
  public ModelAndView addCategoryConfirm(ModelAndView modelAndView,
                                         @ModelAttribute(name = "bindingModel")
                                             CategoryBindingModel bindingModel,
                                         BindingResult bindingResult) {
    if (!bindingResult.hasErrors()) {
      CategoryServiceModel serviceModel = this.modelMapper
          .map(bindingModel, CategoryServiceModel.class);
      this.categoryService.addCategory(serviceModel);
      return super.redirect(ALL_CATEGORIES_ROUTE);
    }

    modelAndView.addObject("bindingModel", bindingModel);
    return super.view(ADD_CATEGORY_VIEW, modelAndView);
  }




  @GetMapping("/fetch")
  @PreAuthorize("isAuthenticated()")
  @ResponseBody
  public List<CategoryViewModel> fetchCategories() {
    return this.categoryService.findAllCategories()
        .stream()
        .map(c -> this.modelMapper.map(c, CategoryViewModel.class))
        .collect(Collectors.toList());
  }


}
