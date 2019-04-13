package com.allsi.eventshare.service;

import com.allsi.eventshare.domain.models.service.CategoryServiceModel;

import java.util.List;

public interface CategoryService {
  CategoryServiceModel findById(String id);

  CategoryServiceModel findByName(String name);

  List<CategoryServiceModel> findAllCategories();

  void addCategory(CategoryServiceModel serviceModel);

  List<CategoryServiceModel> findAllCategoriesWithEvents();
}
