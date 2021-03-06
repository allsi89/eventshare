package com.allsi.eventshare.web.controllers;

import com.allsi.eventshare.domain.entities.Organisation;
import com.allsi.eventshare.repository.ImageRepository;
import com.allsi.eventshare.repository.OrganisationRepository;
import com.allsi.eventshare.repository.UserRepository;
import com.allsi.eventshare.service.user.UserService;
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

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class OrganisationControllerTests {

  @MockBean
  private UserRepository userRepository;
  @MockBean
  private OrganisationRepository organisationRepository;
  @MockBean
  private ImageRepository imageRepository;

  @Mock
  private Principal principal;

  @Autowired
  private MockMvc mockMvc;

  @Test
  public void addOrganisationGet_withNoLoggedUser_returnsLogin() throws Exception {
    mockMvc.perform(get("/organisations/add")
        .principal(this.principal))
        .andExpect(redirectedUrl("http://localhost/users/login"));
  }

  @Test
  @WithMockUser(roles = "USER")
  public void addOrganisationGet_withLoggedUser_returnsCorrect() throws Exception {
    mockMvc.perform(get("/organisations/add")
        .principal(this.principal))
        .andExpect(view().name("organisation/add-organisation"));
  }

  @Test
  @WithMockUser(roles = {"USER", "CORP"}, username = "username")
  public void OrganisationDetailsGet_withLoggedUserRoleCorp_returnsCorrect() throws Exception {
    when(this.organisationRepository.findByUser_Username("username"))
        .thenReturn(java.util.Optional.of(new Organisation()));

    mockMvc.perform(get("/organisations/details")
        .principal(this.principal))
        .andExpect(view().name("organisation/organisation-view"));
  }

  @Test
  @WithMockUser(roles = "USER", username = "username")
  public void OrganisationDetailsGet_withLoggedUserRoleUser_returnsError() throws Exception {
    when(this.organisationRepository.findByUser_Username("username"))
        .thenReturn(java.util.Optional.of(new Organisation()));

    mockMvc.perform(get("/organisations/details")
        .principal(this.principal))
        .andExpect(view().name("error"));
  }

  @Test
  public void OrganisationDetailsGet_withNoLoggedUser_returnsLogin() throws Exception {
    mockMvc.perform(get("/organisations/details"))
        .andExpect(redirectedUrl("http://localhost/users/login"));
  }

}
