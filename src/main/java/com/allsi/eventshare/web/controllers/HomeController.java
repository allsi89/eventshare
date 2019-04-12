package com.allsi.eventshare.web.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;

import static com.allsi.eventshare.constants.GlobalConstants.HOME_VIEW;
import static com.allsi.eventshare.constants.GlobalConstants.INDEX_VIEW;

@Controller
public class HomeController extends BaseController {
  public HomeController() {
      }

  @GetMapping("/error")
  public ModelAndView wrong() {
    return super.view("error/wrong");
  }

  @GetMapping("/unauthorized")
  public ModelAndView unauthorized(){
    return super.view("error/unauthorized");
  }

  @GetMapping("/")
  @PreAuthorize("isAnonymous()")
  public ModelAndView index() {
    return super.view(INDEX_VIEW);
  }

  @GetMapping("/home")
  @PreAuthorize("isAuthenticated()")
  public ModelAndView home(Principal principal, ModelAndView modelAndView) {
    if (principal != null){
      modelAndView.addObject("username", principal.getName());
      return super.view(HOME_VIEW, modelAndView);
    } else {
      return super.view(INDEX_VIEW, modelAndView);
    }
  }

  @GetMapping("/test")
  public ModelAndView modelAndView(){
    return super.view("test");
  }
}
