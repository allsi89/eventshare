package com.allsi.eventshare.service;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MySecurityServiceImpl implements MySecurityService {

  public void resetAuth(String role, boolean isToBeAdded) {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    List<GrantedAuthority> updatedAuthorities;

    if (isToBeAdded) {
      updatedAuthorities = new ArrayList<>(auth.getAuthorities());
      updatedAuthorities.add(new SimpleGrantedAuthority(role));
    } else {
      updatedAuthorities = auth.getAuthorities().stream()
          .filter(a -> !a.getAuthority().equals(role))
          .collect(Collectors.toList());
    }

    Authentication newAuth = new UsernamePasswordAuthenticationToken(
        auth.getPrincipal(),
        auth.getCredentials(),
        updatedAuthorities);
    SecurityContextHolder.getContext().setAuthentication(newAuth);
  }
}