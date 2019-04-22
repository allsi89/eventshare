package com.allsi.eventshare.web.controllers;

import com.allsi.eventshare.domain.entities.Event;
import com.allsi.eventshare.domain.entities.User;
import com.allsi.eventshare.repository.EventRepository;
import com.allsi.eventshare.repository.ImageRepository;
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
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class EventControllerTests {

  @MockBean
  private UserRepository userRepository;
  @MockBean
  private EventRepository eventRepository;
  @MockBean
  protected ImageRepository imageRepository;

  @Mock
  private Principal principal;

  @Autowired
  private MockMvc mockMvc;

  @Test
  public void addEventGet_withNoLoggedUser_returnsLogin() throws Exception {
    mockMvc.perform(get("/events/add")
        .principal(this.principal))
        .andExpect(redirectedUrl("http://localhost/users/login"));
  }

  @Test
  @WithMockUser
  public void addEventGet_withLoggedUser_returnsCorrect() throws Exception {
    mockMvc.perform(get("/events/add")
        .principal(this.principal))
        .andExpect(view().name("event/add-event"));
  }

  @Test
  public void myEventsGet_withNoLoggedUser_returnsLogin() throws Exception {
    mockMvc.perform(get("/events/my-events")
        .principal(this.principal))
        .andExpect(redirectedUrl("http://localhost/users/login"));
  }

  @Test
  @WithMockUser(username = "testUser")
  public void myEventsGet_withLoggedUser_returnsCorrect() throws Exception {
    User user = new User(){{
      setId("testId");
      setUsername("testUser");
    }};

    when(this.userRepository.findByUsername("testUser"))
        .thenReturn(java.util.Optional.of(user));

    List<Event> events = new ArrayList<>();

    when(this.eventRepository.findAllByCreator_IdOrderByStartDatetimeDesc("testUser"))
        .thenReturn(events);

    mockMvc.perform(get("/events/my-events")
        .principal(this.principal))
        .andExpect(view().name("event/user-events"));
  }
}
