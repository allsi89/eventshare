package com.allsi.eventshare.util;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.allsi.eventshare.constants.Constants.CORP;

@Service
public class AuthServiceImpl implements AuthService {

  public void resetAuthCorp(boolean isToBeAdded) {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    List<GrantedAuthority> updatedAuthorities;

    if (isToBeAdded) {
      updatedAuthorities = new ArrayList<>(auth.getAuthorities());
      updatedAuthorities.add(new SimpleGrantedAuthority(CORP));
    } else {
      updatedAuthorities = auth.getAuthorities().stream()
          .filter(a -> !a.getAuthority().equals(CORP))
          .collect(Collectors.toList());
    }

    Authentication newAuth = new UsernamePasswordAuthenticationToken(
        auth.getPrincipal(),
        auth.getCredentials(),
        updatedAuthorities);
    SecurityContextHolder.getContext().setAuthentication(newAuth);
  }
}
