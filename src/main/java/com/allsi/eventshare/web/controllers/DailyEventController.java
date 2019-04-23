package com.allsi.eventshare.web.controllers;

import com.allsi.eventshare.domain.models.view.EventViewModel;
import com.allsi.eventshare.service.event.EventService;
import com.allsi.eventshare.web.annotations.PageTitle;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import static com.allsi.eventshare.common.GlobalConstants.DAILY_EVENT_VIEW;

@Controller
@RequestMapping
public class DailyEventController extends BaseController {
  private final EventService eventService;
  private final ModelMapper modelMapper;

  @Autowired
  public DailyEventController(EventService eventService, ModelMapper modelMapper) {
    this.eventService = eventService;
    this.modelMapper = modelMapper;
  }

  @GetMapping("/daily-event")
  @PreAuthorize("isAuthenticated()")
  @PageTitle("Daily Event")
  public ModelAndView dailyEvent(ModelAndView modelAndView) {

    EventViewModel viewModel = this.modelMapper
        .map( this.eventService.getDailyEvent(), EventViewModel.class);

    modelAndView.addObject("viewModel", viewModel);
    return super.view(DAILY_EVENT_VIEW, modelAndView);
  }
}
