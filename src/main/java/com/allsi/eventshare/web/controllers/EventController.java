package com.allsi.eventshare.web.controllers;

import com.allsi.eventshare.domain.models.binding.AddEventBindingModel;
import com.allsi.eventshare.domain.models.binding.EditEventBindingModel;
import com.allsi.eventshare.domain.models.service.EventServiceModel;
import com.allsi.eventshare.domain.models.view.EventListViewModel;
import com.allsi.eventshare.domain.models.view.EventViewModel;
import com.allsi.eventshare.service.EventService;
import com.allsi.eventshare.service.ImageService;
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
import java.util.List;
import java.util.stream.Collectors;

import static com.allsi.eventshare.constants.GlobalConstants.*;

@Controller
@RequestMapping("/events")
public class EventController extends BaseController {
  private final EventService eventService;
  private final ImageService imageService;
  private final ModelMapper modelMapper;

  @Autowired
  public EventController(EventService eventService, ModelMapper modelMapper, ImageService imageService) {
    this.eventService = eventService;
    this.modelMapper = modelMapper;
    this.imageService = imageService;
  }

  //ADD event - works
  @GetMapping("/add")
  @PreAuthorize("isAuthenticated()")
  public ModelAndView addEvent(ModelAndView modelAndView,
                               @ModelAttribute(name = "bindingModel")
                                   AddEventBindingModel bindingModel) {

    modelAndView.addObject("bindingModel", bindingModel);

    return super.view(ADD_EVENT_VIEW, modelAndView);
  }

  //ADD event post works
  @PostMapping("/add")
  @PreAuthorize(("isAuthenticated()"))
  public ModelAndView addEventConfirm(Principal principal,
                                      ModelAndView modelAndView,
                                      @Valid @ModelAttribute(name = "bindingModel")
                                          AddEventBindingModel bindingModel,
                                      BindingResult bindingResult){

    if (!bindingResult.hasErrors()) {
      EventServiceModel eventServiceModel = this.modelMapper
          .map(bindingModel, EventServiceModel.class);

      eventServiceModel = this.eventService
          .addEvent(eventServiceModel,
              principal.getName());

      return super.redirect(OWNER_EVENT_DETAILS_ROUTE + eventServiceModel.getId());
    }

    modelAndView.addObject("bindingModel", bindingModel);
    return super.view(ADD_EVENT_VIEW, modelAndView);
  }

  //IT works
  @GetMapping("/my-events/view/{id}")
  @PreAuthorize("isAuthenticated()")
  public ModelAndView creatorEventView(Principal principal,
                                       ModelAndView modelAndView,
                                       @PathVariable(name = "id") String eventId) {

    EventServiceModel eventServiceModel = this.eventService
        .findEventByIdAndCreator(eventId, principal.getName());

    EventViewModel viewModel = this.modelMapper
        .map(eventServiceModel, EventViewModel.class);

    modelAndView.addObject("viewModel", viewModel);

    return super.view(OWNER_EVENT_DETAILS_VIEW, modelAndView);
  }

//  @PostMapping("/my-events/view")
//  @PreAuthorize("isAuthenticated()")
//  public ModelAndView creatorEventViewAddPicture(Principal principal,
//                                       ModelAndView modelAndView,
//                                       @RequestParam(name = "eventId") String eventId) {
//
//    EventServiceModel eventServiceModel = this.eventService
//        .findEventByIdAndCreator(eventId, principal.getName());
//
//    EventViewModel viewModel = this.modelMapper
//        .map(eventServiceModel, EventViewModel.class);
//
//    modelAndView.addObject("viewModel", viewModel);
//
//    return super.view(OWNER_EVENT_DETAILS_VIEW, modelAndView);
//  }

//  IT works
  @PostMapping("/add-pictures/{id}")
  @PreAuthorize("isAuthenticated()")
  @ResponseBody
  public ModelAndView addEventPictures(Principal principal,
                                       @RequestParam("file") MultipartFile file,
                                       @PathVariable(name = "id") String id,
                                       ModelAndView modelAndView) throws IOException {

    this.eventService.fillGallery(id, principal.getName(), this.imageService.saveInDb(file));
    return super.redirect(OWNER_EVENT_DETAILS_ROUTE + id);
  }

  //It Works
  @GetMapping("/my-events")
  @PreAuthorize("isAuthenticated()")
  public ModelAndView allUserEvents(Principal principal, ModelAndView modelAndView) {

    List<EventListViewModel> events =  this.eventService
        .findAllByCreator(principal.getName())
        .stream()
        .map(e -> this.modelMapper.map(e, EventListViewModel.class))
        .collect(Collectors.toList());
    modelAndView.addObject("events", events);

    return super.view(OWNER_ALL_EVENTS_VIEW, modelAndView);
  }

  @PostMapping("/my-events/delete")
  @PreAuthorize("isAuthenticated()")
  public ModelAndView deleteEventConfirm(Principal principal,
                                       @RequestParam("deleteId") String id) {

    this.eventService.deleteEvent(id, principal.getName());

    return super.redirect(OWNER_ALL_EVENTS_ROUTE);
  }

  @GetMapping("/my-events/edit")
  @PreAuthorize("isAuthenticated()")
  public ModelAndView editEvent(@RequestParam("editId") String id,
                                @ModelAttribute("bindingModel")
                                    EditEventBindingModel bindingModel,
                                ModelAndView modelAndView) {
    System.out.println();
    bindingModel = this.modelMapper
        .map(this.eventService.findEventById(id), EditEventBindingModel.class);

    modelAndView.addObject("bindingModel", bindingModel);
    return super.view(EDIT_EVENT_VIEW, modelAndView);
  }

  //TODO edit

  @PostMapping("/my-events/edit")
  @PreAuthorize("isAuthenticated()")
  public ModelAndView editEventConfirm(Principal principal,
                                       @RequestParam(name = "id") String id,
                                       @Valid @ModelAttribute("bindingModel")
                                           EditEventBindingModel bindingModel,
                                       ModelAndView modelAndView,
                                       BindingResult bindingResult) {

    if (!bindingResult.hasErrors()){
      EventServiceModel serviceModel = this.modelMapper
          .map(bindingModel, EventServiceModel.class);

      serviceModel.setId(id);

      this.eventService.editEvent(serviceModel, principal.getName());

      return super.redirect(OWNER_EVENT_DETAILS_ROUTE + serviceModel.getId());
    }

    modelAndView.addObject("bindingModel", bindingModel);
    return super.view(EDIT_EVENT_VIEW, modelAndView);
  }

}
