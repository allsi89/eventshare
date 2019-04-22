package com.allsi.eventshare.web.controllers;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.security.Principal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class HomeControllerTests {

  @Mock
  private Principal principal;

  @Autowired
  private MockMvc mockMvc;

  @Test
  public void homeGet_withNoLoggedUser_returnsLogin() throws Exception {
    mockMvc.perform(get("/home")
        .principal(this.principal))
        .andExpect(redirectedUrl("http://localhost/users/login"));
  }

  @Test
  @WithMockUser(username = "testUser")
  public void homeGet_withLoggedUser_returnsHome() throws Exception {
    mockMvc.perform(get("/home")
        .principal(this.principal))
        .andExpect(view().name("home"));
  }

  @Test
  public void indexGet_withNoLoggedUser_returnsIndex() throws Exception {
    mockMvc.perform(get("/")
        .principal(this.principal))
        .andExpect(view().name("index"));
  }

  @Test
  @WithMockUser(username = "testUser")
  public void indexGet_withLoggedUser_returnsError() throws Exception {
    mockMvc.perform(get("/")
        .principal(this.principal))
        .andExpect(status().isForbidden());
  }
}
