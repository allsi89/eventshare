package com.allsi.eventshare.web.controllers;

import com.allsi.eventshare.util.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;

public abstract class BaseController {

  @Autowired
  private AuthService authService;

  protected ModelAndView view(String view) {
    return this.view(view, new ModelAndView());
  }

  protected ModelAndView view(String view, ModelAndView modelAndView) {
    modelAndView.setViewName(view);
    return modelAndView;
  }

  ModelAndView redirect(String url) {
    return this.view("redirect:" + url);
  }

  ModelAndView redirect(String url, boolean isToBeAdded) {
    this.authService.resetAuthCorp(isToBeAdded);
    return this.view("redirect:" + url);
  }
}
