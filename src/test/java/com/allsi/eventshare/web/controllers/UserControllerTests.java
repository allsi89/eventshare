package com.allsi.eventshare.web.controllers;

import com.allsi.eventshare.domain.entities.User;
import com.allsi.eventshare.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.security.Principal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTests {

  @MockBean
  private UserRepository userRepository;

  @Mock
  private Principal principal;

  @Autowired
  private MockMvc mockMvc;

  @Test
  public void loginGet_withNoLoggedUser_returnsCorrect() throws Exception {
    mockMvc.perform(get("/users/login"))
        .andExpect(view().name("user/login"))
        .andExpect(status().isOk());
  }

  @Test
  public void registerGet_withNoLoggedUser_returnsCorrect() throws Exception {
    mockMvc.perform(get("/users/register"))
        .andExpect(view().name("user/register"))
        .andExpect(status().isOk());
  }

  @Test
  @WithMockUser
  public void loginGet_withLoggedUser_returnsError() throws Exception {
    mockMvc.perform(get("/users/login"))
        .andExpect(view().name("error"))
        .andExpect(status().isOk());
  }

  @Test
  @WithMockUser
  public void registerGet_withLoggedUser_returnsForbidden() throws Exception {
    mockMvc.perform(get("/users/register"))
        .andExpect(status().isForbidden());
  }

  @Test
  public void registerPost_withValidData_returnsCorrectRegisteredUser() throws Exception {
    mockMvc.perform(post("/users/register")
        .with(csrf())
        .param("username", "valid_user")
        .param("email", "validuser@gmail.com")
        .param("password", "12345")
        .param("confirmPassword", "12345"))
        .andExpect(view().name("redirect:/users/login"));

    verify(this.userRepository).save(any());
  }

  @Test
  public void registerPost_withNotValidData_returnsErrors() throws Exception {
    mockMvc.perform(post("/users/register")
        .with(csrf())
        .param("username", "not_valid_user")
        .param("email", "not_valid")
        .param("password", "12")
        .param("confirmPassword", "123"))
        .andExpect(view().name("user/register"));

    verify(this.userRepository, never()).save(any());
  }

  @Test
  @WithMockUser
  public void userProfileGet_withLoggedUser_returnsCorrect() throws Exception {
    when(this.userRepository.findByUsername(anyString()))
        .thenReturn(java.util.Optional.of(new User()));

    mockMvc.perform(get("/users/profile")
    .principal(this.principal))
        .andExpect(view().name("user/profile-view"));
  }

  @Test
  public void userProfileGet_withNoLoggedUser_returnsLogin() throws Exception {
    when(this.userRepository.findByUsername(anyString()))
        .thenReturn(java.util.Optional.of(new User()));

    mockMvc.perform(get("/users/profile"))
        .andExpect(redirectedUrl("http://localhost/users/login"));
  }

  @Test
  @WithMockUser
  public void userProfileGet_withNoCorrectLoggedUser_returnsError() throws Exception {
    mockMvc.perform(get("/users/profile")
    .principal(this.principal))
        .andExpect(view().name("error"));
  }

  @Test
  @WithMockUser
  public void userProfileEditGet_withLoggedUser_returnsCorrect() throws Exception {
    when(this.userRepository.findByUsername(anyString()))
        .thenReturn(java.util.Optional.of(new User()));

    mockMvc.perform(get("/users/profile/edit")
        .principal(this.principal))
        .andExpect(view().name("user/edit-profile"));
  }

  @Test
  @WithMockUser
  public void userEditPasswordGet_withLoggedUser_returnsCorrect() throws Exception {
    when(this.userRepository.findByUsername(anyString()))
        .thenReturn(java.util.Optional.of(new User()));

    mockMvc.perform(get("/users/password/edit")
        .principal(this.principal))
        .andExpect(view().name("user/edit-password"));
  }

}
