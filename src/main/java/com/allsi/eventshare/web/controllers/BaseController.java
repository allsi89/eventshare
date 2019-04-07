package com.allsi.eventshare.web.controllers;

import com.allsi.eventshare.service.MySecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;

public abstract class BaseController {

  @Autowired
  private MySecurityService securityService;



  protected ModelAndView view(String view, ModelAndView modelAndView) {
    modelAndView.setViewName(view);
    return modelAndView;
  }

  protected ModelAndView view(String view) {
    return this.view(view, new ModelAndView());
  }

  protected ModelAndView redirect(String url, String role, boolean isToBeAdded) {
    this.securityService.resetAuth(role, isToBeAdded);
    return this.view("redirect:" + url);
  }

  protected ModelAndView redirect(String url) {
    return this.view("redirect:" + url);
  }
}
