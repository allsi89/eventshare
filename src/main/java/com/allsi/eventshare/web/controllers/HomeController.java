package com.allsi.eventshare.web.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;

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
    return super.view("index");
  }

  @GetMapping("/home")
  @PreAuthorize("isAuthenticated()")
  public ModelAndView home(Principal principal, ModelAndView modelAndView) {
    if (principal != null){
      modelAndView.addObject("username", principal.getName());
      return super.view("home", modelAndView);
    } else {
      return super.view("index", modelAndView);
    }
  }
}
