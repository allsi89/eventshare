package com.allsi.eventshare.service;

import com.allsi.eventshare.domain.models.service.CategoryServiceModel;

import java.util.List;

public interface CategoryService {
  public CategoryServiceModel findById(String id);

  public CategoryServiceModel findByName(String name);

  List<CategoryServiceModel> findAllCategories();

  void addCategory(CategoryServiceModel serviceModel);
}
