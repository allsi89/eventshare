package com.allsi.eventshare.web.interceptors;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class FaviconInterceptor extends HandlerInterceptorAdapter {
  @Override
  public void postHandle(HttpServletRequest request,
                         HttpServletResponse response,
                         Object handler,
                         ModelAndView modelAndView) {

    String link = "https://res.cloudinary.com/event-share/image/upload/v1555620825/favicon.png";

    if (modelAndView != null) {
      modelAndView.addObject("favicon", link);
    }
  }
}
