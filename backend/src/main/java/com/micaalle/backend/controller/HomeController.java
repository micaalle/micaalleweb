package com.micaalle.backend.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.micaalle.entity.PhotoEntry;
import com.micaalle.entity.Project;
import com.micaalle.entity.RecentPost;
import com.micaalle.service.PhotoEntryService;
import com.micaalle.service.ProjectService;
import com.micaalle.service.RecentPostService;
import com.micaalle.web.dto.HomeTimelineEntry;

@Controller
public class HomeController {

    private final ProjectService projectService;
    private final RecentPostService recentPostService;
    private final PhotoEntryService photoEntryService;

    public HomeController(
            ProjectService projectService,
            RecentPostService recentPostService,
            PhotoEntryService photoEntryService) {
        this.projectService = projectService;
        this.recentPostService = recentPostService;
        this.photoEntryService = photoEntryService;
    }

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("activePage", "home");
        model.addAttribute("featuredProjects", projectService.getFeaturedProjects(3));
        model.addAttribute("recentPosts", recentPostService.getRecentPosts(4));
        model.addAttribute("timelineEntries", timelineEntries());
        model.addAttribute("currentFocusItems", currentFocusItems());
        return "pages/home";
    }

    @GetMapping("/about")
    public String aboutRedirect() {
        return "redirect:/";
    }

    @GetMapping("/links")
    public String linksRedirect() {
        return "redirect:/contact";
    }

    @GetMapping("/projects")
    public String projects(Model model) {
        model.addAttribute("activePage", "projects");
        model.addAttribute("projects", projectService.getAllProjects());
        return "pages/projects";
    }

    @GetMapping("/projects/{slug}")
    public String projectDetail(@PathVariable String slug, Model model) {
        Project project = projectService.getProjectBySlug(slug);
        model.addAttribute("activePage", "projects");
        model.addAttribute("project", project);
        model.addAttribute("relatedProjects", projectService.getRelatedProjects(slug, 2));
        return "pages/project-detail";
    }

    @GetMapping("/recent")
    public String recent(Model model) {
        model.addAttribute("activePage", "recent");
        model.addAttribute("posts", recentPostService.getAllPosts());
        return "pages/recent";
    }

    @GetMapping("/recent/{slug}")
    public String recentDetail(@PathVariable String slug, Model model) {
        RecentPost post = recentPostService.getPostBySlug(slug);
        model.addAttribute("activePage", "recent");
        model.addAttribute("post", post);
        model.addAttribute("recentPosts", recentPostService.getRecentPostsExcluding(slug, 3));
        return "pages/recent-detail";
    }

    @GetMapping("/photos")
    public String photos(Model model) {
        model.addAttribute("activePage", "photos");
        model.addAttribute("photosByYear", photoEntryService.getPhotosGroupedByYear());
        model.addAttribute("photoCount", photoEntryService.getAllPhotos().size());
        return "pages/photos";
    }

    @GetMapping("/photos/{id}")
    public String photoDetail(@PathVariable Long id, Model model) {
        PhotoEntry photo = photoEntryService.getPhotoById(id);
        model.addAttribute("activePage", "photos");
        model.addAttribute("photo", photo);
        return "pages/photo-detail";
    }

    @GetMapping("/contact")
    public String contact(Model model) {
        model.addAttribute("activePage", "contact");
        return "pages/contact";
    }

    private List<HomeTimelineEntry> timelineEntries() {
        return List.of(
                new HomeTimelineEntry(
                        "Now",
                        "2026",
                        "Looking for work",
                        "Software Engineer roles",
                        "Working on personal projects while I apply to software engineering positions."),
                new HomeTimelineEntry(
                        "Education",
                        "2022–2025",
                        "University of Central Florida",
                        "B.S. in Computer Science",
                        "Software Development, Artificial Intelligence, Bioinformatic Algorithms, Natural Language Processing, Senior Design, Object-Oriented Programming, Game Development, Computer Graphics, Cybersecurity.")
        );
    }

    private List<String> currentFocusItems() {
        return List.of(
                "Finishing the Vulkan Spectral Ocean project by fixing implementations of spray and water foaming.",
                "GameBoy Advance Emulator. A long-term project to emulate the GBA without needing the 16KB bios file",
                "3D room mapping tool using a rotating LiDAR sensor with reconstruction via WebGL."
        );
    }
}
