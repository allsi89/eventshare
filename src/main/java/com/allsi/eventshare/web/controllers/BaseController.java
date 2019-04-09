package com.allsi.eventshare.web.controllers;

import com.allsi.eventshare.service.AuthUpdateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;

public abstract class BaseController {

  @Autowired
  private AuthUpdateService authUpdateService;



  protected ModelAndView view(String view, ModelAndView modelAndView) {
    modelAndView.setViewName(view);
    return modelAndView;
  }

  protected ModelAndView view(String view) {
    return this.view(view, new ModelAndView());
  }

  protected ModelAndView redirect(String url, boolean isToBeAdded) {
    this.authUpdateService.resetAuthCorp(isToBeAdded);
    return this.view("redirect:" + url);
  }

  protected ModelAndView redirect(String url) {
    return this.view("redirect:" + url);
  }
}
