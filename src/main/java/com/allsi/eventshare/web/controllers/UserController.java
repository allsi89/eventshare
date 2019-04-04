package com.allsi.eventshare.web.controllers;

import com.allsi.eventshare.domain.models.binding.UserBindingModel;
import com.allsi.eventshare.domain.models.service.UserServiceModel;
import com.allsi.eventshare.domain.models.view.OrganisationViewModel;
import com.allsi.eventshare.domain.models.view.UserProfileViewModel;
import com.allsi.eventshare.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.security.Principal;

@Controller
@RequestMapping("/users")
public class UserController extends BaseController {
  private final UserService userService;
  private final ModelMapper modelMapper;

  @Autowired
  public UserController(UserService userService, ModelMapper modelMapper) {
    this.userService = userService;
    this.modelMapper = modelMapper;
  }

  @GetMapping("/login")
  @PreAuthorize("isAnonymous()")
  public ModelAndView login() {
    return super.view("login");
  }

  @GetMapping("/register")
  @PreAuthorize("isAnonymous()")
  public ModelAndView register() {
    return super.view("register");
  }

  @PostMapping("/register")
  @PreAuthorize("isAnonymous()")
  public ModelAndView registerConfirm(@Valid @ModelAttribute(name = "bindingModel")
                                          UserBindingModel bindingModel,
                                      BindingResult bindingResult,
                                      ModelAndView modelAndView) {
    if (!bindingModel.getPassword().equals(bindingModel.getConfirmPassword()) ||
        bindingResult.hasErrors()) {
      return super.view("register");
    }

    boolean isSuccessful = this.userService
        .register(this.modelMapper.map(bindingModel, UserServiceModel.class));
    if (isSuccessful) {
      return super.redirect("/login");
    }

    return super.view("register");

  }

  @GetMapping("/profile")
  @PreAuthorize("isAuthenticated()")
  public ModelAndView profile(Principal principal) {
    ModelAndView modelAndView = getProfileModelAndView(principal);

    return super.view("profile", modelAndView);
  }

  @GetMapping("/profile/edit")
  @PreAuthorize("isAuthenticated()")
  public ModelAndView editProfile(Principal principal) {
    ModelAndView modelAndView = getProfileModelAndView(principal);

    return super.view("edit-profile", modelAndView);
  }

  @PostMapping("/profile/edit")
  @PreAuthorize("isAuthenticated()")
  public ModelAndView editProfileConfirm(@Valid @ModelAttribute(name = "userModel")
                                             UserBindingModel userModel,
                                         BindingResult bindingResult,
                                         ModelAndView modelAndView) {
    if (!userModel.getPassword().equals(userModel.getConfirmPassword()) ||
        bindingResult.hasErrors()) {
      return super.view("edit-profile");
    }

    boolean isSuccessful = this.userService.editUserProfile(this.modelMapper
        .map(userModel, UserServiceModel.class), userModel.getOldPassword());

    if (isSuccessful){
      return super.redirect("/users/profile");
    }

    return super.view("edit-profile");
  }

  private ModelAndView getProfileModelAndView(Principal principal) {
    UserServiceModel userServiceModel = this.userService
        .findUserByUsername(principal.getName());

    ModelAndView modelAndView = new ModelAndView();

    UserProfileViewModel viewModel = this.modelMapper
        .map(userServiceModel, UserProfileViewModel.class);

    if (userServiceModel.getCorporate()) {

      OrganisationViewModel organisationViewModel = this.userService
          .findUserOrganisation(principal.getName());

      viewModel.setOrganisation(organisationViewModel);
    }

    modelAndView
        .addObject("userModel", this.modelMapper
            .map(userServiceModel, UserProfileViewModel.class));

    return modelAndView;
  }
}
