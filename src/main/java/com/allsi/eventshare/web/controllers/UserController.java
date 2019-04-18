package com.allsi.eventshare.web.controllers;

import com.allsi.eventshare.domain.models.binding.UserEditBindingModel;
import com.allsi.eventshare.domain.models.binding.UserEditPasswordBindingModel;
import com.allsi.eventshare.domain.models.binding.UserRegisterBindingModel;
import com.allsi.eventshare.domain.models.service.UserServiceModel;
import com.allsi.eventshare.domain.models.view.UserViewModel;
import com.allsi.eventshare.service.ImageService;
import com.allsi.eventshare.service.UserService;
import com.allsi.eventshare.validation.user.UserChangePasswordValidator;
import com.allsi.eventshare.validation.user.UserEditProfileValidator;
import com.allsi.eventshare.validation.user.UserRegisterValidator;
import com.allsi.eventshare.web.annotations.PageTitle;
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

import static com.allsi.eventshare.common.GlobalConstants.*;

@Controller
@RequestMapping("/users")
public class UserController extends BaseController {
  private final UserService userService;
  private final ImageService imageService;
  private final UserRegisterValidator userRegisterValidator;
  private final UserEditProfileValidator userEditProfileValidator;
  private final UserChangePasswordValidator changePasswordValidator;
  private final ModelMapper modelMapper;

  @Autowired
  public UserController(UserService userService, ImageService imageService, UserRegisterValidator userRegisterValidator, UserEditProfileValidator userEditProfileValidator, UserChangePasswordValidator changePasswordValidator, ModelMapper modelMapper) {
    this.userService = userService;
    this.imageService = imageService;
    this.userRegisterValidator = userRegisterValidator;
    this.userEditProfileValidator = userEditProfileValidator;
    this.changePasswordValidator = changePasswordValidator;
    this.modelMapper = modelMapper;
  }

  @GetMapping("/login")
  @PreAuthorize("isAnonymous()")
  @PageTitle("Login")
  public ModelAndView login() {
    return super.view(USER_LOGIN_VIEW);
  }

  @GetMapping("/register")
  @PreAuthorize("isAnonymous()")
  @PageTitle("Register")
  public ModelAndView register(ModelAndView modelAndView,
                               @ModelAttribute("bindingModel")
                                   UserRegisterBindingModel bindingModel) {
    modelAndView.addObject("bindingModel", bindingModel);
    return super.view(USER_REGISTER_VIEW, modelAndView);
  }

  @PostMapping("/register")
  @PreAuthorize("isAnonymous()")
  public ModelAndView registerConfirm(@Valid @ModelAttribute(name = "bindingModel")
                                          UserRegisterBindingModel bindingModel,
                                      BindingResult bindingResult,
                                      ModelAndView modelAndView) {

    this.userRegisterValidator.validate(bindingModel, bindingResult);

    if (!bindingResult.hasErrors()) {
      this.userService
          .register(this.modelMapper
              .map(bindingModel, UserServiceModel.class));

      return super.redirect(USER_LOGIN_ROUTE);
    }

    modelAndView.addObject("bindingModel", bindingModel);
    return super.view(USER_REGISTER_VIEW, modelAndView);
  }

  @GetMapping("/profile")
  @PreAuthorize("isAuthenticated()")
  @PageTitle("Profile")
  public ModelAndView profile(Principal principal, ModelAndView modelAndView) {

    UserServiceModel userServiceModel = this.userService
        .findUserByUsername(principal.getName());

    UserViewModel userModel = this.modelMapper.map(userServiceModel, UserViewModel.class);

    modelAndView.addObject("userModel", userModel);
    return super.view(USER_PROFILE_VIEW, modelAndView);
  }

  @GetMapping("/profile/edit")
  @PreAuthorize("isAuthenticated()")
  @PageTitle("Edit Profile")
  public ModelAndView editProfile(Principal principal,
                                  @ModelAttribute(name = "userModel")
                                      UserEditBindingModel userModel,
                                  ModelAndView modelAndView) {

    userModel = this.modelMapper
        .map(this.userService.findUserByUsername(principal.getName()),
            UserEditBindingModel.class);
    modelAndView
        .addObject("userModel", userModel);

    return super.view(USER_EDIT_PROFILE_VIEW, modelAndView);
  }

  @PostMapping("/profile/edit")
  @PreAuthorize("isAuthenticated()")
  public ModelAndView editProfileConfirm(@Valid @ModelAttribute(name = "userModel")
                                             UserEditBindingModel userModel,
                                         BindingResult bindingResult,
                                         ModelAndView modelAndView) {

    this.userEditProfileValidator.validate(userModel, bindingResult);

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
  @PageTitle("Change Password")
  public ModelAndView editPassword(@ModelAttribute(name = "userModel")
                                         UserEditPasswordBindingModel userModel,
                                   ModelAndView modelAndView) {

    userModel = new UserEditPasswordBindingModel();
    modelAndView.addObject("userModel", userModel);
    return super.view(USER_CHANGE_PASSWORD_VIEW, modelAndView);
  }

  @PostMapping("/password/edit")
  @PreAuthorize("isAuthenticated()")
  public ModelAndView editPasswordConfirm(Principal principal,
                                          @Valid @ModelAttribute("userModel")
                                              UserEditPasswordBindingModel userModel,
                                          BindingResult bindingResult,
                                          ModelAndView modelAndView) {

    userModel.setUsername(principal.getName());

    this.changePasswordValidator.validate(userModel, bindingResult);

    if (!bindingResult.hasErrors()) {
      this.userService.editUserPassword(
          this.modelMapper.map(userModel, UserServiceModel.class),
          principal.getName(),
          userModel.getOldPassword());

      return super.redirect(USER_PROFILE_ROUTE);
    }

    modelAndView.addObject("userModel", userModel);
    return super.view(USER_CHANGE_PASSWORD_VIEW, modelAndView);
  }


  @PostMapping("/profile/change-picture")
  @PreAuthorize("isAuthenticated()")
  public ModelAndView changeProfilePicture(Principal principal,
                                           @RequestParam("file") MultipartFile file) throws IOException {

    this.userService.editUserPicture(principal.getName(),
        this.imageService.saveInDb(file));
    return super.redirect(USER_PROFILE_ROUTE);
  }
}
