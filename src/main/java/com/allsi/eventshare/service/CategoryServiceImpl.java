package com.allsi.eventshare.service;

import com.allsi.eventshare.domain.entities.Category;
import com.allsi.eventshare.domain.models.service.CategoryServiceModel;
import com.allsi.eventshare.repository.CategoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {
  private static final String CATEGORY_NOT_FOUND = "Category not found!";

  private final CategoryRepository categoryRepository;
  private final ModelMapper modelMapper;

  @Autowired
  public CategoryServiceImpl(CategoryRepository categoryRepository, ModelMapper modelMapper) {
    this.categoryRepository = categoryRepository;
    this.modelMapper = modelMapper;
  }

  @Override
  public CategoryServiceModel findById(String id) {
    Category category = this.categoryRepository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException(CATEGORY_NOT_FOUND));

    return this.modelMapper.map(category, CategoryServiceModel.class);
  }

  @Override
  public CategoryServiceModel findByName(String name) {
    Category category = this.categoryRepository.findByName(name)
        .orElseThrow(() -> new IllegalArgumentException(CATEGORY_NOT_FOUND));
    return this.modelMapper.map(category, CategoryServiceModel.class);
  }

  @Override
  public List<CategoryServiceModel> findAllCategories() {
    return this.categoryRepository.findAll()
        .stream()
        .map(c->this.modelMapper.map(c,CategoryServiceModel.class))
        .collect(Collectors.toList());
  }

  @Override
  public void addCategory(CategoryServiceModel serviceModel) {
    //TODO validate
    Category category = this.modelMapper.map(serviceModel, Category.class);
    this.categoryRepository.saveAndFlush(category);
  }
}
