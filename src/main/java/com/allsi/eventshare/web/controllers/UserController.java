package com.allsi.eventshare.web.controllers;

import com.allsi.eventshare.domain.models.binding.UserEditBindingModel;
import com.allsi.eventshare.domain.models.binding.UserEditPasswordBindingModel;
import com.allsi.eventshare.domain.models.binding.UserRegisterBindingModel;
import com.allsi.eventshare.domain.models.service.UserServiceModel;
import com.allsi.eventshare.domain.models.view.UserProfileViewModel;
import com.allsi.eventshare.service.CloudService;
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

@Controller
@RequestMapping("/users")
public class UserController extends BaseController {
  private final UserService userService;
  private final CloudService cloudService;
  private final ModelMapper modelMapper;

  @Autowired
  public UserController(UserService userService, CloudService cloudService, ModelMapper modelMapper) {
    this.userService = userService;
    this.cloudService = cloudService;
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
                                          UserRegisterBindingModel bindingModel,
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
  public ModelAndView profile(Principal principal, ModelAndView modelAndView) {
    modelAndView
        .addObject("userModel", this.getProfileViewModel(principal));

    return super.view("profile", modelAndView);
  }

  @GetMapping("/profile/edit")
  @PreAuthorize("isAuthenticated()")
  public ModelAndView editProfile(Principal principal, ModelAndView modelAndView) {
    modelAndView
        .addObject("userModel", this.getProfileViewModel(principal));

    return super.view("edit-profile", modelAndView);
  }

  @PostMapping("/profile/edit")
  @PreAuthorize("isAuthenticated()")
  public ModelAndView editProfileConfirm(@Valid @ModelAttribute(name = "userModel")
                                             UserEditBindingModel userModel,
                                         @RequestParam("file") MultipartFile file,
                                         BindingResult bindingResult) throws IOException {

    if (bindingResult.hasErrors()) {
      return super.view("edit-profile");
    }

    UserServiceModel userServiceModel = this.modelMapper
        .map(userModel, UserServiceModel.class);

    if (file != null) {
      userServiceModel.setImageUrl(this.cloudService.uploadImage(file));
    }

    this.userService.editUserProfile(userServiceModel);

    return super.redirect("/users/profile");
  }

  @GetMapping("/password/edit")
  @PreAuthorize("isAuthenticated()")
  public ModelAndView editPassword(){
    return super.view("edit-password");
  }

  @PostMapping("/password/edit")
  @PreAuthorize("isAuthenticated()")
  public ModelAndView editPasswordConfirm(Principal principal,
                                          @ModelAttribute("userModel")
                                              UserEditPasswordBindingModel userModel,
                                          BindingResult bindingResult){
    if (bindingResult.hasErrors()){
      return super.view("edit-password");
    }

    this.userService.editUserPassword(this.modelMapper
        .map(userModel, UserServiceModel.class),
        principal.getName(),
        userModel.getOldPassword());

    return super.redirect("/users/profile");
  }

  private UserProfileViewModel getProfileViewModel(Principal principal) {
    UserServiceModel userServiceModel = this.userService
        .findUserByUsername(principal.getName());

    return this.modelMapper
        .map(userServiceModel, UserProfileViewModel.class);
  }
}
