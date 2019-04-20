package com.allsi.eventshare.service.category;

import com.allsi.eventshare.domain.models.service.CategoryServiceModel;

import java.util.List;

public interface CategoryService {
  CategoryServiceModel findById(String id);

  List<CategoryServiceModel> findAllCategories();

  void addCategory(CategoryServiceModel serviceModel);

  List<CategoryServiceModel> findAllCategoriesWithEvents();

  void editCategory(String categoryName, String id);

  void deleteCategory(String id);
}
