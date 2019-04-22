package com.allsi.eventshare.web.controllers;

import com.allsi.eventshare.domain.entities.Category;
import com.allsi.eventshare.domain.entities.Country;
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
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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

  @Test
  public void editEventGet_withNoLoggedUser_returnsLogin() throws Exception {
    Event event = new Event(){{
      setId("id");
      setName("name");
      setCreator(new User());
      setStartDatetime(LocalDateTime.now());
      setCountry(new Country());
      setCity("city");
      setCategory(new Category());
    }};

    when(this.eventRepository.findById("id"))
        .thenReturn(java.util.Optional.of(event));

    mockMvc.perform(get("/events/my-events/edit")
        .param("editId", "id"))
        .andExpect(redirectedUrl("http://localhost/users/login"));
  }

  @Test
  @WithMockUser(username = "user")
  public void editEventGet_withLoggedUser_returnsCorrect() throws Exception {



    Event event = new Event(){{
      setId("id");
      setName("name");
      setCreator(new User());
      setStartDatetime(LocalDateTime.now());
      setCountry(new Country());
      setCity("city");
      setCategory(new Category());
    }};

    when(this.eventRepository.findById("id"))
        .thenReturn(java.util.Optional.of(event));

    mockMvc.perform(get("/events/my-events/edit" )
        .param("editId", "id"))
        .andExpect(view().name("event/edit-event"));
  }


  @Test
  public void deleteEventGet_withNoLoggedUser_returnsLogin() throws Exception {

    Event event = new Event(){{
      setId("id");
      setName("name");
      setCreator(new User());
      setStartDatetime(LocalDateTime.now());
      setCountry(new Country());
      setCity("city");
      setCategory(new Category());
    }};

    when(this.eventRepository.findById("id"))
        .thenReturn(java.util.Optional.of(event));

    mockMvc.perform(post("/events/my-events/delete")
        .with(csrf())
        .param("deleteId", "id"))
        .andExpect(redirectedUrl("http://localhost/users/login"));
  }

  @Test
  @WithMockUser(username = "user")
  public void deleteEventPost_withLoggedUser_returnsCorrect() throws Exception {
    User user = new User() {{
      setId("userId");
      setUsername("user");
    }};

    Country country = new Country() {{
      setId("countryId");
      setNiceName("countryNicename");
    }};

    Event event = new Event(){{
      setId("id");
      setName("name");
      setCreator(user);
      setStartDatetime(LocalDateTime.now());
      setCountry(country);
      setCity("city");
      setCategory(new Category());
    }};

    when(this.eventRepository.findById("id"))
        .thenReturn(java.util.Optional.of(event));

    mockMvc.perform(post("/events/my-events/delete" )
        .with(csrf())
        .principal(this.principal)
        .param("deleteId", "id"))
        .andExpect(view().name("redirect:/events/my-events"));

    verify(this.eventRepository).deleteById("id");

  }
}
