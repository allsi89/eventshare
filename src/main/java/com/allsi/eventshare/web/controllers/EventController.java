package com.allsi.eventshare.web.controllers;

import com.allsi.eventshare.domain.models.binding.EventBindingModel;
import com.allsi.eventshare.domain.models.service.EventServiceModel;
import com.allsi.eventshare.domain.models.view.EventBriefViewModel;
import com.allsi.eventshare.domain.models.view.EventViewModel;
import com.allsi.eventshare.domain.models.view.ImageViewModel;
import com.allsi.eventshare.service.EventService;
import com.allsi.eventshare.service.ImageService;
import com.allsi.eventshare.service.UserService;
import com.allsi.eventshare.util.AuthService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.io.IOException;
import java.security.Principal;
import java.text.ParseException;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/events")
public class EventController extends BaseController {
  private final EventService eventService;
  private final ImageService imageService;
  private final UserService userService;
  private final AuthService authService;
  private final ModelMapper modelMapper;

  @Autowired
  public EventController(EventService eventService, ModelMapper modelMapper, ImageService imageService, UserService userService, AuthService authService) {
    this.eventService = eventService;
    this.modelMapper = modelMapper;
    this.imageService = imageService;
    this.userService = userService;
    this.authService = authService;
  }

  @GetMapping("/add")
  @PreAuthorize("isAuthenticated()")
  public ModelAndView addEvent(ModelAndView modelAndView,
                               @ModelAttribute(name = "bindingModel")
                                   EventBindingModel bindingModel) {

    modelAndView.addObject("bindingModel", bindingModel);

    return super.view("add-event", modelAndView);
  }

  @PostMapping("/add")
  @PreAuthorize(("isAuthenticated()"))
  public ModelAndView addEventConfirm(Principal principal,
                                      ModelAndView modelAndView,
                                      @Valid @ModelAttribute(name = "bindingModel")
                                          EventBindingModel bindingModel,
                                      BindingResult bindingResult) throws ParseException {

    if (!bindingResult.hasErrors()) {
      EventServiceModel eventServiceModel = this.modelMapper
          .map(bindingModel, EventServiceModel.class);

      eventServiceModel = this.eventService
          .addEvent(eventServiceModel, principal.getName(), bindingModel.getCountryNiceName(), bindingModel.getStartsOnDate());

      return super.redirect("/events/my-events/created/" + eventServiceModel.getId());
    }

    modelAndView.addObject("bindingModel", bindingModel);
    return super.view("add-event", modelAndView);
  }

  @GetMapping("/my-events/created/{eventId}")
  @PreAuthorize("isAuthenticated()")
  public ModelAndView creatorEventView(Principal principal,
                                       ModelAndView modelAndView,
                                       @PathVariable(name = "eventId") String eventId) {

    EventServiceModel eventServiceModel = this.eventService.findEventByIdAndCreator(eventId, principal.getName());

    EventViewModel viewModel = this.modelMapper
        .map(eventServiceModel, EventViewModel.class);

    //TODO - mappings -- check if these work
//    viewModel.setCountry(eventServiceModel.getCountry().getNiceName());

    modelAndView.addObject("viewModel", viewModel);

    return super.view("creator-event-view", modelAndView);
  }

  @PostMapping("/add-pictures/{id}")
  @PreAuthorize("isAuthenticated()")
  public ModelAndView addEventPictures(Principal principal,
                                       @RequestParam("file") MultipartFile file,
                                       @PathVariable(name = "id") String id) throws IOException {

    this.eventService.fillGallery(id, principal.getName(), this.imageService.saveInDb(file));

    return super.redirect("/events/my-events/created/" + id);
  }

  @GetMapping(value = "/all-pictures/{id}", produces = "application/json")
  @ResponseBody
  public Object fetchEventPictures(@PathVariable(name = "id") String id) {

    EventServiceModel eventServiceModel = this.eventService.findEventById(id);

    return eventServiceModel.getImages()
        .stream()
        .map(img -> this.modelMapper
            .map(img, ImageViewModel.class))
        .collect(Collectors.toList());
  }

  @GetMapping(value = "/my-events/created", produces = "application/json")
  @ResponseBody
  public Object fetchCreated() {
    return this.eventService.findAllByCreator(this.authService.getPrincipalUsername())
        .stream()
        .map(e -> this.modelMapper.map(e, EventBriefViewModel.class))
        .collect(Collectors.toList());
  }

  @GetMapping(value = "/my-events/attending", produces = "application/json")
  @ResponseBody
  public Object fetchAttending() {

    List<String> eventsIds = this.userService.findUserAttendingEvents(this.authService.getPrincipalUsername());

    return this.eventService.findAllById(eventsIds)
        .stream()
        .map(e -> this.modelMapper.map(e, EventBriefViewModel.class))
        .collect(Collectors.toList());
  }

  @GetMapping("/my-events")
  @PreAuthorize("isAuthenticated()")
  public ModelAndView allUserEvents() {
    return super.view("user-events");
  }

  @GetMapping("/my-events/attending/{id}")
  @PreAuthorize("isAuthenticated()")
  public ModelAndView attendingEventView(Principal principal,
                                         @PathVariable(name = "id") String id,
                                         ModelAndView modelAndView) {

    this.eventService.checkRegistrationForEvent(id, principal.getName());

    EventBriefViewModel eventBriefViewModel = this.modelMapper
        .map(this.eventService.findEventById(id), EventBriefViewModel.class);

    modelAndView.addObject("viewModel", eventBriefViewModel);

    return super.view("attendee-event-view", modelAndView);
  }

  @PostMapping("/events/my-events/attending/remove/{id}")
  @PreAuthorize("isAuthenticated()")
  public ModelAndView removeFromAttendanceList(Principal principal,
                                               @PathVariable(name = "id") String id) {

    this.eventService.removeAttendanceEvent(principal.getName(), id);
    return super.redirect("/events/my-events");
  }


}
