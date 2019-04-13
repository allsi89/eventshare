package com.allsi.eventshare.web.controllers;

import com.allsi.eventshare.domain.models.binding.UserEditBindingModel;
import com.allsi.eventshare.domain.models.binding.UserEditPasswordBindingModel;
import com.allsi.eventshare.domain.models.binding.UserRegisterBindingModel;
import com.allsi.eventshare.domain.models.service.UserServiceModel;
import com.allsi.eventshare.domain.models.view.UserProfileViewModel;
import com.allsi.eventshare.service.ImageService;
import com.allsi.eventshare.service.UserService;
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

import static com.allsi.eventshare.constants.GlobalConstants.*;

@Controller
@RequestMapping("/users")
public class UserController extends BaseController {
  private final UserService userService;
  private final ImageService imageService;
  private final ModelMapper modelMapper;

  @Autowired
  public UserController(UserService userService, ImageService imageService, ModelMapper modelMapper) {
    this.userService = userService;
    this.imageService = imageService;
    this.modelMapper = modelMapper;
  }

  @GetMapping("/login")
  @PreAuthorize("isAnonymous()")
  public ModelAndView login() {
    return super.view(USER_LOGIN_VIEW);
  }

  @GetMapping("/register")
  @PreAuthorize("isAnonymous()")
  public ModelAndView register() {
    return super.view(USER_REGISTER_VIEW);
  }

  @PostMapping("/register")
  @PreAuthorize("isAnonymous()")
  public ModelAndView registerConfirm(@Valid @ModelAttribute(name = "bindingModel")
                                          UserRegisterBindingModel bindingModel,
                                      BindingResult bindingResult,
                                      ModelAndView modelAndView) {

    if (bindingModel.getPassword().equals(bindingModel.getConfirmPassword()) &&
        !bindingResult.hasErrors()) {

      if (this.userService
          .register(this.modelMapper
              .map(bindingModel, UserServiceModel.class))) {
        return super.redirect(USER_LOGIN_ROUTE);
      }
    }

    modelAndView.addObject("bindingModel", bindingModel);
    return super.view(USER_REGISTER_VIEW, modelAndView);
  }

  @GetMapping("/profile")
  @PreAuthorize("isAuthenticated()")
  public ModelAndView profile(Principal principal, ModelAndView modelAndView) {
    modelAndView
        .addObject("userModel", this.getProfileViewModel(principal));

    return super.view(USER_PROFILE_VIEW, modelAndView);
  }

  @GetMapping("/profile/edit")
  @PreAuthorize("isAuthenticated()")
  public ModelAndView editProfile(Principal principal, ModelAndView modelAndView) {
    modelAndView
        .addObject("userModel", this.getProfileViewModel(principal));

    return super.view(USER_EDIT_PROFILE_VIEW, modelAndView);
  }

  @PostMapping("/profile/edit")
  @PreAuthorize("isAuthenticated()")
  public ModelAndView editProfileConfirm(@Valid @ModelAttribute(name = "userModel")
                                             UserEditBindingModel userModel,
                                         BindingResult bindingResult,
                                         ModelAndView modelAndView) {

    if (!bindingResult.hasErrors()) {

      UserServiceModel userServiceModel = this.modelMapper
          .map(userModel, UserServiceModel.class);

      this.userService.editUserProfile(userServiceModel);

      return super.redirect(USER_PROFILE_ROUTE);
    }

    modelAndView.addObject("userModel", userModel);

    return super.view(USER_EDIT_PROFILE_VIEW, modelAndView);
  }

  @GetMapping("/password/edit")
  @PreAuthorize("isAuthenticated()")
  public ModelAndView editPassword() {
    return super.view(USER_CHANGE_PASSWORD_VIEW);
  }

  @PostMapping("/password/edit")
  @PreAuthorize("isAuthenticated()")
  public ModelAndView editPasswordConfirm(Principal principal,
                                          @Valid @ModelAttribute("userModel")
                                              UserEditPasswordBindingModel userModel,
                                          BindingResult bindingResult,
                                          ModelAndView modelAndView) {
    if (!bindingResult.hasErrors()) {
      this.userService.editUserPassword(this.modelMapper
              .map(userModel, UserServiceModel.class),
          principal.getName(),
          userModel.getOldPassword());

      return super.redirect(USER_PROFILE_ROUTE);
    }

    modelAndView.addObject("userModel", userModel);

    return super.view(USER_CHANGE_PASSWORD_VIEW);
  }

  private UserProfileViewModel getProfileViewModel(Principal principal) {
    UserServiceModel userServiceModel = this.userService
        .findUserByUsername(principal.getName());

    return this.modelMapper
        .map(userServiceModel, UserProfileViewModel.class);
  }


  @PostMapping("/profile/change-picture")
  @PreAuthorize("isAuthenticated()")
  public ModelAndView changeProfilePicture(Principal principal,
                                           @RequestParam("file") MultipartFile file) throws IOException {

    this.userService.editUserPicture(principal.getName(), this.imageService.saveInDb(file));
    return super.redirect(USER_PROFILE_ROUTE);
  }


}
