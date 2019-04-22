package com.allsi.eventshare.web.controllers;

import com.allsi.eventshare.domain.entities.Category;
import com.allsi.eventshare.repository.CategoryRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class CategoryControllerTests {
  @MockBean
  private CategoryRepository categoryRepository;

  @Autowired
  private MockMvc mockMvc;

  @Test
  public void allCategoriesGet_withNoLoggedUser_returnsLogin() throws Exception {
    mockMvc.perform(get("/categories/all"))
        .andExpect(redirectedUrl("http://localhost/users/login"));
  }

  @Test
  @WithMockUser
  public void allCategoriesGet_withLoggedUserNotAuthorized_returnsError() throws Exception {
    mockMvc.perform(get("/categories/all"))
        .andExpect(view().name("error"));
  }

  @Test
  @WithMockUser(roles = "MODERATOR")
  public void allCategoriesGet_withLoggedUserModerator_returnsCorrect() throws Exception {
    mockMvc.perform(get("/categories/all"))
        .andExpect(view().name("category/all-categories"));
  }

  @Test
  @WithMockUser(roles = "ADMIN")
  public void allCategoriesGet_withLoggedUserAdmin_returnsCorrect() throws Exception {
    mockMvc.perform(get("/categories/all"))
        .andExpect(view().name("category/all-categories"));
  }

  @Test
  @WithMockUser(roles = "ROOT")
  public void allCategoriesGet_withLoggedUserRoot_returnsCorrect() throws Exception {
    mockMvc.perform(get("/categories/all"))
        .andExpect(view().name("category/all-categories"));
  }

  /*
  Category add post method
   */
  @Test
  public void addCategoryPost_withNoLoggedUser_returnsLogin() throws Exception {
    mockMvc.perform(post("/categories/add")
        .with(csrf())
        .param("name", "category"))
        .andExpect(redirectedUrl("http://localhost/users/login"));
  }

  @Test
  @WithMockUser
  public void addCategoryPost_withLoggedUserNotAuthorized_returnsError() throws Exception {
    mockMvc.perform(post("/categories/add")
        .with(csrf())
        .param("name", "category"))
        .andExpect(view().name("error"));
  }

  @Test
  @WithMockUser(roles = "MODERATOR")
  public void addCategoryPost_withLoggedUserModerator_returnsCorrect() throws Exception {
    mockMvc.perform(post("/categories/add")
        .with(csrf())
        .param("name", "category"))
        .andExpect(view().name("redirect:/categories/all"));

    verify(this.categoryRepository).save(any());
  }

  @Test
  @WithMockUser(roles = "ADMIN")
  public void addCategoryPost_withLoggedUserAdmin_returnsCorrect() throws Exception {
    mockMvc.perform(post("/categories/add")
        .with(csrf())
        .param("name", "category"))
        .andExpect(view().name("redirect:/categories/all"));

    verify(this.categoryRepository).save(any());
  }

  @Test
  @WithMockUser(roles = "ROOT")
  public void addCategoryPost_withLoggedUserRoot_returnsCorrect() throws Exception {
    mockMvc.perform(post("/categories/add")
        .with(csrf())
        .param("name", "category"))
        .andExpect(view().name("redirect:/categories/all"));

    verify(this.categoryRepository).save(any());
  }

  @Test
  @WithMockUser(roles = "ROOT")
  public void addCategoryPost_withLoggedUserRootNoValidData_returnsCorrect() throws Exception {
    mockMvc.perform(post("/categories/add")
        .with(csrf())
        .param("name", ""))
        .andExpect(model().attributeHasFieldErrors("bindingModel", "name"));
  }

/*
Add category Get method
 */

  @Test
  public void addCategoryGet_withNoLoggedUser_returnsLogin() throws Exception {
    mockMvc.perform(get("/categories/add"))
        .andExpect(redirectedUrl("http://localhost/users/login"));
  }

  @Test
  @WithMockUser
  public void addCategoryGet_withLoggedUserNotAuthorized_returnsError() throws Exception {
    mockMvc.perform(get("/categories/add"))
        .andExpect(view().name("error"));
  }

  @Test
  @WithMockUser(roles = "MODERATOR")
  public void addCategoryGet_withLoggedUserModerator_returnsCorrect() throws Exception {
    mockMvc.perform(get("/categories/add"))
        .andExpect(view().name("category/add-category"));
  }

  @Test
  @WithMockUser(roles = "ADMIN")
  public void addCategoryGet_withLoggedUserAdmin_returnsCorrect() throws Exception {
    mockMvc.perform(get("/categories/add"))
        .andExpect(view().name("category/add-category"));
  }

  @Test
  @WithMockUser(roles = "ROOT")
  public void addCategoryGet_withLoggedUserRoot_returnsCorrect() throws Exception {
    mockMvc.perform(get("/categories/add"))
        .andExpect(view().name("category/add-category"));
  }


  /*
 Category edit post method
  */
  @Test
  public void editCategoryPost_withNoLoggedUser_returnsLogin() throws Exception {

    Category category = new Category() {{
      setId("id");
      setName("name");
    }};

    when(this.categoryRepository.findById("id"))
        .thenReturn(java.util.Optional.of(category));

    mockMvc.perform(post("/categories/edit/{id}", "id")
        .with(csrf())
        .param("name", "category"))
        .andExpect(redirectedUrl("http://localhost/users/login"));

  }

  @Test
  @WithMockUser
  public void editCategoryPost_withLoggedUserNotAuthorized_returnsError() throws Exception {
    Category category = new Category() {{
      setId("id");
      setName("name");
    }};

    when(this.categoryRepository.findById("id"))
        .thenReturn(java.util.Optional.of(category));

    mockMvc.perform(post("/categories/edit/{id}", "id")
        .with(csrf())
        .param("name", "category"))
        .andExpect(view().name("error"));
  }

  @Test
  @WithMockUser(roles = "MODERATOR")
  public void editCategoryPost_withLoggedUserModerator_returnsCorrect() throws Exception {
    Category category = new Category() {{
      setId("id");
      setName("name");
    }};

    when(this.categoryRepository.findById("id"))
        .thenReturn(java.util.Optional.of(category));

    mockMvc.perform(post("/categories/edit/{id}", "id")
        .with(csrf())
        .param("name", "category"))
        .andExpect(view().name("error"));
  }

  @Test
  @WithMockUser(roles = "ADMIN")
  public void editCategoryPost_withLoggedUserAdmin_returnsCorrect() throws Exception {
    Category category = new Category() {{
      setId("id");
      setName("name");
    }};

    when(this.categoryRepository.findById("id"))
        .thenReturn(java.util.Optional.of(category));

    mockMvc.perform(post("/categories/edit/{id}", "id")
        .with(csrf())
        .param("name", "category"))
        .andExpect(view().name("redirect:/categories/all"));

    verify(this.categoryRepository).save(category);

  }

  @Test
  @WithMockUser(roles = "ROOT")
  public void editCategoryPost_withLoggedUserRoot_returnsCorrect() throws Exception {
    Category category = new Category() {{
      setId("id");
      setName("name");
    }};

    when(this.categoryRepository.findById("id"))
        .thenReturn(java.util.Optional.of(category));

    mockMvc.perform(post("/categories/edit/{id}", "id")
        .with(csrf())
        .param("name", "category"))
        .andExpect(view().name("redirect:/categories/all"));

    verify(this.categoryRepository).save(category);

  }

  @Test
  @WithMockUser(roles = "ROOT")
  public void editCategoryPost_withLoggedUserRootNoValidData_returnsCorrect() throws Exception {
    Category category = new Category() {{
      setId("id");
      setName("name");
    }};

    when(this.categoryRepository.findById("id"))
        .thenReturn(java.util.Optional.of(category));

    mockMvc.perform(post("/categories/edit/{id}", "id")
        .with(csrf())
        .param("name", ""))
        .andExpect(model().attributeHasFieldErrors("bindingModel", "name"));
  }

/*
Edit category Get method
 */

  @Test
  public void editCategoryGet_withNoLoggedUser_returnsLogin() throws Exception {
    Category category = new Category() {{
      setId("id");
      setName("name");
    }};

    when(this.categoryRepository.findById("id"))
        .thenReturn(java.util.Optional.of(category));

    mockMvc.perform(get("/categories/edit/{id}", "id"))
        .andExpect(redirectedUrl("http://localhost/users/login"));
  }

  @Test
  @WithMockUser
  public void editCategoryGet_withLoggedUserNotAuthorized_returnsError() throws Exception {
    Category category = new Category() {{
      setId("id");
      setName("name");
    }};

    when(this.categoryRepository.findById("id"))
        .thenReturn(java.util.Optional.of(category));

    mockMvc.perform(get("/categories/edit/{id}", "id"))
        .andExpect(view().name("error"));
  }

  @Test
  @WithMockUser(roles = "MODERATOR")
  public void editCategoryGet_withLoggedUserModerator_returnsCorrect() throws Exception {
    Category category = new Category() {{
      setId("id");
      setName("name");
    }};

    when(this.categoryRepository.findById("id"))
        .thenReturn(java.util.Optional.of(category));

    mockMvc.perform(get("/categories/edit/{id}", "id"))
        .andExpect(view().name("error"));
  }

  @Test
  @WithMockUser(roles = "ADMIN")
  public void editCategoryGet_withLoggedUserAdmin_returnsCorrect() throws Exception {
    Category category = new Category() {{
      setId("id");
      setName("name");
    }};

    when(this.categoryRepository.findById("id"))
        .thenReturn(java.util.Optional.of(category));

    mockMvc.perform(get("/categories/edit/{id}", "id"))
        .andExpect(view().name("category/edit-category"));
  }

  @Test
  @WithMockUser(roles = "ROOT")
  public void editCategoryGet_withLoggedUserRoot_returnsCorrect() throws Exception {
    Category category = new Category() {{
      setId("id");
      setName("name");
    }};

    when(this.categoryRepository.findById("id"))
        .thenReturn(java.util.Optional.of(category));

    mockMvc.perform(get("/categories/edit/{id}", "id"))
        .andExpect(view().name("category/edit-category"));
  }

  /*
 Category delete post method
  */
  @Test
  public void deleteCategoryPost_withNoLoggedUser_returnsLogin() throws Exception {

    Category category = new Category() {{
      setId("id");
      setName("name");
    }};

    when(this.categoryRepository.findById("id"))
        .thenReturn(java.util.Optional.of(category));

    mockMvc.perform(post("/categories/delete/{id}", "id")
        .with(csrf()))
        .andExpect(redirectedUrl("http://localhost/users/login"));

  }

  @Test
  @WithMockUser
  public void deleteCategoryPost_withLoggedUserNotAuthorized_returnsError() throws Exception {
    Category category = new Category() {{
      setId("id");
      setName("name");
    }};

    when(this.categoryRepository.findById("id"))
        .thenReturn(java.util.Optional.of(category));

    mockMvc.perform(post("/categories/delete/{id}", "id")
        .with(csrf()))
        .andExpect(view().name("error"));
  }

  @Test
  @WithMockUser(roles = "MODERATOR")
  public void deleteCategoryPost_withLoggedUserModerator_returnsCorrect() throws Exception {
    Category category = new Category() {{
      setId("id");
      setName("name");
    }};

    when(this.categoryRepository.findById("id"))
        .thenReturn(java.util.Optional.of(category));

    mockMvc.perform(post("/categories/delete/{id}", "id")
        .with(csrf()))
        .andExpect(view().name("error"));
  }

  @Test
  @WithMockUser(roles = "ADMIN")
  public void deleteCategoryPost_withLoggedUserAdmin_returnsCorrect() throws Exception {
    Category category = new Category() {{
      setId("id");
      setName("name");
    }};

    when(this.categoryRepository.findById("id"))
        .thenReturn(java.util.Optional.of(category));

    mockMvc.perform(post("/categories/delete/{id}", "id")
        .with(csrf()))
        .andExpect(view().name("redirect:/categories/all"));

    verify(this.categoryRepository).deleteById(anyString());

  }

  @Test
  @WithMockUser(roles = "ROOT")
  public void deleteCategoryPost_withLoggedUserRoot_returnsCorrect() throws Exception {
    Category category = new Category() {{
      setId("id");
      setName("name");
    }};

    when(this.categoryRepository.findById("id"))
        .thenReturn(java.util.Optional.of(category));

    mockMvc.perform(post("/categories/delete/{id}", "id")
        .with(csrf()))
        .andExpect(view().name("redirect:/categories/all"));

    verify(this.categoryRepository).deleteById(anyString());
  }
}
