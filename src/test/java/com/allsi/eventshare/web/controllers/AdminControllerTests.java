package com.allsi.eventshare.web.controllers;

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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class AdminControllerTests {
  @MockBean
  private UserRepository userRepository;

  @Mock
  private Principal principal;

  @Autowired
  private MockMvc mockMvc;

  @Test
  @WithMockUser(roles = {"ROOT", "ADMIN"}, username = "testUser")
  public void allUsersGet_withValidCredentials_returnsCorrect() throws Exception {
    mockMvc.perform(get("/admin/all-users")
    .principal(this.principal))
        .andExpect(view().name("/admin/all-users"));
  }

  @Test
  @WithMockUser(roles = "MODERATOR", username = "testUser")
  public void allUsersGet_withUserModerator_returnsNotAllowed() throws Exception {
    mockMvc.perform(get("/admin/all-users")
        .principal(this.principal))
        .andExpect(view().name("error"));
  }

  @Test
  @WithMockUser(roles = "USER", username = "testUser")
  public void allUsersGet_withUserUserRole_returnsNotAllowed() throws Exception {
    mockMvc.perform(get("/admin/all-users")
        .principal(this.principal))
        .andExpect(view().name("error"));
  }

  @Test
  public void allUsersGet_withNotLogged_returnsLogin() throws Exception {
    mockMvc.perform(get("/admin/all-users")
        .principal(this.principal))
        .andExpect(redirectedUrl("http://localhost/users/login"));
  }
}
