package com.allsi.eventshare.web.controllers;

import com.allsi.eventshare.web.annotations.PageTitle;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;

import static com.allsi.eventshare.common.GlobalConstants.HOME_VIEW;
import static com.allsi.eventshare.common.GlobalConstants.INDEX_VIEW;

@Controller
public class HomeController extends BaseController {
  public HomeController() {
  }

  @GetMapping("/")
  @PreAuthorize("isAnonymous()")
  @PageTitle("Index")
  public ModelAndView index() {
    return super.view(INDEX_VIEW);
  }

  @GetMapping("/home")
  @PreAuthorize("isAuthenticated()")
  @PageTitle("Home")
  public ModelAndView home(Principal principal, ModelAndView modelAndView) {
    if (principal != null) {
      modelAndView.addObject("username", principal.getName());
      return super.view(HOME_VIEW, modelAndView);
    } else {
      return super.view(INDEX_VIEW, modelAndView);
    }
  }
}
