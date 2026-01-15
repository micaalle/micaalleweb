package com.micaalle.backend.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("title", "Home");
        model.addAttribute("content", "pages/home :: content"); // Thymeleaf fragment
        model.addAttribute("projects", List.of());   // placeholder, replace with DB later
        model.addAttribute("experience", List.of()); // placeholder, replace with DB later
        return "layout/base"; // points to templates/layout/base.html
    }
}
