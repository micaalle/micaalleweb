package com.micaalle.backend.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "pages/home";
    }

    @GetMapping("/about")
    public String about() {
        return "pages/about";
    }

    @GetMapping("/projects")
    public String projects() {
        return "pages/projects";
    }

    @GetMapping("/links")
    public String links() {
        return "pages/links";
    }

    @GetMapping("/contact")
    public String contact() {
        return "pages/contact";
    }
}
