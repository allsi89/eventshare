package com.allsi.eventshare.service;

import com.allsi.eventshare.domain.entities.Category;
import com.allsi.eventshare.domain.entities.Event;
import com.allsi.eventshare.domain.models.service.CategoryServiceModel;
import com.allsi.eventshare.errors.CategoryNotFoundException;
import com.allsi.eventshare.errors.IllegalOperationException;
import com.allsi.eventshare.repository.CategoryRepository;
import com.allsi.eventshare.repository.EventRepository;
import com.allsi.eventshare.service.category.CategoryService;
import com.allsi.eventshare.service.category.CategoryServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class CategoryServiceTests {
  private static final String CATEGORY_ID = "someId";
  private static final String CATEGORY_SECOND_ID = "someId2";
  private static final String CATEGORY_NAME = "aName";
  private static final String CATEGORY_SECOND_NAME = "bName";

  @Mock
  private CategoryRepository categoryRepository;
  @Mock
  private EventRepository eventRepository;

  @Spy
  private ModelMapper modelMapper;

  private CategoryService categoryService;

  @Before
  public void init() {
    this.categoryService = new CategoryServiceImpl(this.categoryRepository,
        this.eventRepository, this.modelMapper);
  }

  @Test
  public void findById_withValidId_returnsCorrect() {
    Category category = new Category() {{
      setId(CATEGORY_ID);
      setName(CATEGORY_NAME);
    }};

    when(this.categoryRepository.findById(CATEGORY_ID))
        .thenReturn(
            Optional.of(category));

    CategoryServiceModel serviceModel = this.categoryService.findById(CATEGORY_ID);

    Assert.assertEquals(serviceModel.getId(), category.getId());
    Assert.assertEquals(serviceModel.getName(), category.getName());
  }

  @Test(expected = CategoryNotFoundException.class)
  public void findById_withNotValidId_throwsException() {
    when(this.categoryRepository.findById(CATEGORY_ID))
        .thenReturn(Optional.empty());
    this.categoryService.findById(CATEGORY_ID);
  }

  @Test
  public void findAllCategories_withCategories_returnsCorrect() {
    //method orderByName not tested - since it comes from JPA repo
    when(this.categoryRepository.findAllOrderByName())
        .thenReturn(new ArrayList<>() {{
          add(new Category());
          add(new Category());
          add(new Category());
        }});

    Assert.assertEquals(3, this.categoryService.findAllCategories().size());
  }

  @Test
  public void findAllCategories_withNoCategories_returnsEmpty() {
    when(this.categoryRepository.findAllOrderByName())
        .thenReturn(new ArrayList<>());
    Assert.assertTrue(this.categoryService.findAllCategories().isEmpty());
  }

  @Test
  public void addCategory_withCorrectCategory_returnsCorrect() {
    this.categoryService.addCategory(new CategoryServiceModel());
    verify(this.categoryRepository).save(any());
  }

  @Test
  public void findAllCategoriesWithEvents_returnsCorrect() {
    Category category = new Category() {{
      setId(CATEGORY_ID);
      setName(CATEGORY_NAME);
    }};
    Category category2 = new Category() {{
      setId(CATEGORY_SECOND_ID);
      setName(CATEGORY_SECOND_NAME);
    }};

    List<Event> events = new ArrayList<>() {{
      add(
          new Event() {{
            setCategory(category2);
          }}
      );
      add(
          new Event() {{
            setCategory(category);
          }}
      );
    }};

    when(this.eventRepository.findAllGroupByCategory())
        .thenReturn(events);

    List<CategoryServiceModel> categories = this.categoryService.findAllCategoriesWithEvents();

    Assert.assertEquals(2, categories.size());
    Assert.assertEquals(categories.get(0).getId(), category.getId());
    Assert.assertEquals(categories.get(1).getId(), category2.getId());
  }

  @Test
  public void findAllCategoriesWithEvents_withNoEvents_returnsCorrect() {
    List<CategoryServiceModel> categories = this.categoryService
        .findAllCategoriesWithEvents();

    Assert.assertTrue(categories.isEmpty());
  }

  @Test
  public void editCategory_withCorrectId_returnsCorrect() {
    Category category = new Category() {{
      setId(CATEGORY_ID);
      setName(CATEGORY_NAME);
    }};

    when(this.categoryRepository.findById(CATEGORY_ID))
        .thenReturn(Optional.of(category));

    this.categoryService.editCategory(CATEGORY_NAME, CATEGORY_ID);
    verify(this.categoryRepository).save(any());
  }

  @Test(expected = CategoryNotFoundException.class)
  public void editCategory_withNotCorrectId_throwsException() {
    this.categoryService.editCategory(CATEGORY_NAME, CATEGORY_ID);
  }

  @Test
  public void deleteCategory_withCorrectId_returnsCorrect() {

    Category category = new Category() {{
      setId(CATEGORY_ID);
    }};

    when(this.categoryRepository.findById(CATEGORY_ID))
        .thenReturn(Optional.of(category));

    this.categoryService.deleteCategory(CATEGORY_ID);

    verify(this.categoryRepository).deleteById(CATEGORY_ID);
  }

  @Test(expected = IllegalOperationException.class)
  public void deleteCategory_withNotCorrectId_throwsException() {
    this.categoryService.deleteCategory(null);
  }

  @Test(expected = IllegalOperationException.class)
  public void deleteCategory_withCorrectIdAndNoEvents_throwsException() {
    when(this.eventRepository.findAllByCategory_Id(CATEGORY_ID))
        .thenReturn(new ArrayList<>() {{
          add(new Event());
        }});
    this.categoryService.deleteCategory(CATEGORY_ID);
  }
}

