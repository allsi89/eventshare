package com.allsi.eventshare.web.controllers;

import com.allsi.eventshare.domain.models.service.UserServiceModel;
import com.allsi.eventshare.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.security.Principal;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin")
public class AdminController extends BaseController {
  private final UserService userService;
  private final ModelMapper modelMapper;

  @Autowired
  public AdminController(UserService userService, ModelMapper modelMapper) {
    this.userService = userService;
    this.modelMapper = modelMapper;
  }


//  @GetMapping
//  @PreAuthorize("hasRole('ROLE_ADMIN')")
//  public ModelAndView adminPanel(Principal principal, ModelAndView modelAndView,
//                                 @ModelAttribute(name = "viewModel") UserViewModel viewModel) {
//    List<UserViewModel> userList = this.userService.findAllUsers(principal.getName())
//        .stream()
//        .map(u -> this.modelMapper.map(u,UserViewModel.class))
//        .collect(Collectors.toList());
//
//    modelAndView.addObject("users", userList);
//    return super.view("users", modelAndView);
//  }
//
//  @GetMapping("/edit/{id}")
//  @PreAuthorize("hasRole('ROLE_ADMIN')")
//  public ModelAndView editUser(@PathVariable("id") String userId,
//                               @ModelAttribute(name = "editModel")
//                                   ModelAndView modelAndView){
//
//    UserBindingModel editModel = this.modelMapper
//        .map(this.userService.findById(userId), UserBindingModel.class);
//
//    modelAndView.addObject("editModel", editModel);
//    modelAndView.addObject("rolesList", this.userService.listAllRoles());
//
//    return super.view("edit-user", modelAndView);
//
//  }
//
//  @PostMapping("/edit/{id}")
//  @PreAuthorize("hasRole('ROLE_ADMIN')")
//  public ModelAndView editUserConfirm(@PathVariable("id") String userId,
//                                      @Valid @ModelAttribute(name = "editModel")
//                                          UserBindingModel editModel,
//                                      BindingResult bindingResult,
//                                      ModelAndView modelAndView){
//    if (bindingResult.hasErrors()){
//      modelAndView.addObject("rolesList", this.userService.listAllRoles());
//      return super.view("edit-user", modelAndView);
//    }
//
//    editModel.setId(userId);
//    UserServiceModel model =  this.modelMapper.map(editModel, UserServiceModel.class);
//    if (!this.userService.update(model)){
//      return super.view("/error/wrong");
//    }
//
//    return super.redirect("/home");
//  }
}
