package com.michael.biblioteca_thymeleaf.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class GlobalControllerAdvice {

  @Value("${backend.url}")
  private String backendUrl;

  @ModelAttribute
  public void addGlobalAttributes(Model model) {
    model.addAttribute("backendUrl", backendUrl);
  }
}
