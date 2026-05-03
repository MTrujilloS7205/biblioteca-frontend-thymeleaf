package com.michael.biblioteca_thymeleaf.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class HomeController {
  
  @Value("${backend.url}")
  private String backendUrl;

  @GetMapping("/")
  public String menu(Model model) {
    model.addAttribute("backendUrl", backendUrl);
    return "menu"; // menu.html del template
  }
}
