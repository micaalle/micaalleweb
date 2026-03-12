package com.micaalle.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.micaalle.entity.Project;
import com.micaalle.service.ProjectService;

@Controller
@RequestMapping
public class ProjectController {

    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping("/api/projects")
    @ResponseBody
    public List<Project> getAllProjects() {
        return projectService.getAllProjects();
    }

    @GetMapping("/projects/{slug}")
    public String getProjectPage(@PathVariable String slug, Model model) {
        model.addAttribute("project", projectService.getProjectBySlug(slug));
        return "pages/project-detail";
    }
}
