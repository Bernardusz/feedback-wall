package io.github.bernardusz.feedbackwall.exception;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
  @ExceptionHandler
  public String handleException(Exception e, Model model) {
    model.addAttribute("error", e.getMessage());
    return "error";
  }
}
