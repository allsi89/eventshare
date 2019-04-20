package com.allsi.eventshare.web.controllers;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import static com.allsi.eventshare.common.GlobalConstants.ERROR_VIEW;

@ControllerAdvice
public class GlobalExceptionHandler extends BaseController {
  @ExceptionHandler({Throwable.class})
  public ModelAndView handleSqlException(Throwable e) {
    ModelAndView modelAndView = new ModelAndView(ERROR_VIEW);

    Throwable throwable = e;

    while (throwable.getCause() != null) {
      throwable = throwable.getCause();
    }

    modelAndView.addObject("message", throwable.getMessage());

    return modelAndView;
  }
}
