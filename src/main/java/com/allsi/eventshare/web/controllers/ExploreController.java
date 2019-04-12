package com.allsi.eventshare.web.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/explore")
public class ExploreController extends BaseController{

  @GetMapping()
  @PreAuthorize("isAuthenticated()")
  public ModelAndView explore() {

    return super.view("explore");
  }
}
