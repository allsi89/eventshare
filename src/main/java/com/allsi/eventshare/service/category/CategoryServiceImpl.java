package com.allsi.eventshare.service.category;

import com.allsi.eventshare.domain.entities.Category;
import com.allsi.eventshare.domain.entities.Event;
import com.allsi.eventshare.domain.models.service.CategoryServiceModel;
import com.allsi.eventshare.errors.CategoryNotFoundException;
import com.allsi.eventshare.errors.IllegalOperationException;
import com.allsi.eventshare.repository.CategoryRepository;
import com.allsi.eventshare.repository.EventRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static com.allsi.eventshare.common.GlobalConstants.CATEGORY_NOT_FOUND;

@Service
public class CategoryServiceImpl implements CategoryService {
  private static final String OPERATION_NOT_ALLOWED = "Operation not allowed.";

  private final CategoryRepository categoryRepository;
  private final EventRepository eventRepository;
  private final ModelMapper modelMapper;

  @Autowired
  public CategoryServiceImpl(CategoryRepository categoryRepository, EventRepository eventRepository, ModelMapper modelMapper) {
    this.categoryRepository = categoryRepository;
    this.eventRepository = eventRepository;
    this.modelMapper = modelMapper;
  }

  @Override
  public CategoryServiceModel findById(String id) {
    Category category = this.categoryRepository.findById(id)
        .orElseThrow(() -> new CategoryNotFoundException(CATEGORY_NOT_FOUND));

    return this.modelMapper.map(category, CategoryServiceModel.class);
  }

  @Override
  public List<CategoryServiceModel> findAllCategories() {
    return this.categoryRepository.findAllOrderByName()
        .stream()
        .map(c -> this.modelMapper.map(c, CategoryServiceModel.class))
        .collect(Collectors.toList());
  }

  @Override
  public void addCategory(CategoryServiceModel serviceModel) {
    Category category = this.modelMapper.map(serviceModel, Category.class);
    this.categoryRepository.save(category);
  }

  @Override
  public List<CategoryServiceModel> findAllCategoriesWithEvents() {
    List<Event> events = this.eventRepository.findAllGroupByCategory();

    List<CategoryServiceModel> categories = new ArrayList<>();

    for (Event event : events) {
      categories
          .add(
              this.modelMapper
                  .map(event.getCategory(),
                      CategoryServiceModel.class));
    }

    categories.sort(Comparator.comparing(CategoryServiceModel::getName));

    return categories;
  }

  @Override
  public void editCategory(String categoryName, String id) {
    Category category = this.categoryRepository.findById(id)
        .orElseThrow(() -> new CategoryNotFoundException(CATEGORY_NOT_FOUND));
    category.setName(categoryName);
    this.categoryRepository.save(category);
  }

  @Override
  public void deleteCategory(String id) {
    if (this.eventRepository.findAllByCategory_Id(id).size() > 0 || !this.categoryRepository.findById(id).isPresent()) {
      throw new IllegalOperationException(OPERATION_NOT_ALLOWED);
    }

    this.categoryRepository.deleteById(id);
  }
}
