package com.allsi.eventshare.web.controllers;

import com.allsi.eventshare.domain.models.service.RoleServiceModel;
import com.allsi.eventshare.domain.models.view.UserAllViewModel;
import com.allsi.eventshare.service.user.UserService;
import com.allsi.eventshare.web.annotations.PageTitle;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.allsi.eventshare.common.GlobalConstants.*;

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

  @GetMapping("/all-users")
  @PreAuthorize("hasAnyRole('ROLE_ADMIN, ROLE_ROOT')")
  @PageTitle("All Users")
  public ModelAndView adminPanel(Principal principal, ModelAndView modelAndView,
                                 @ModelAttribute(name = "viewModel") UserAllViewModel viewModel) {
    List<UserAllViewModel> userList = this.userService
        .findAllUsersButRequester(principal.getName())
        .stream()
        .map(u -> {
          UserAllViewModel model = this.modelMapper.map(u, UserAllViewModel.class);
          Set<String> authorities = u.getRoles()
              .stream()
              .map(RoleServiceModel::getAuthority)
              .collect(Collectors.toSet());
          model.setAuthorities(authorities);
          return model;
        })
        .collect(Collectors.toList());

    modelAndView.addObject("users", userList);
    return super.view(ADMIN_ALL_USERS_VIEW, modelAndView);
  }

  @PostMapping("/set-user/{id}")
  @PreAuthorize("hasAnyRole('ROLE_ADMIN, ROLE_ROOT_ADMIN')")
  public ModelAndView setUser(@PathVariable String id) {
    this.userService.updateRole(id, USER);

    return super.redirect(ADMIN_ALL_USERS_ROUTE);
  }

  @PostMapping("/set-moderator/{id}")
  @PreAuthorize("hasAnyRole('ROLE_ADMIN, ROLE_ROOT')")
  public ModelAndView setModerator(@PathVariable String id) {
    this.userService.updateRole(id, MODERATOR);

    return super.redirect(ADMIN_ALL_USERS_ROUTE);
  }

  @PostMapping("/set-admin/{id}")
  @PreAuthorize("hasAnyRole('ROLE_ADMIN, ROLE_ROOT')")
  public ModelAndView setAdmin(@PathVariable String id) {
    this.userService.updateRole(id, ADMIN);

    return super.redirect(ADMIN_ALL_USERS_ROUTE);
  }

}
