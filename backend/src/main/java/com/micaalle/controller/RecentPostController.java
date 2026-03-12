package com.micaalle.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.micaalle.entity.RecentPost;
import com.micaalle.service.RecentPostService;

@RestController
@RequestMapping("/api/recent")
@CrossOrigin(origins = "*")
public class RecentPostController {

    private final RecentPostService recentPostService;

    public RecentPostController(RecentPostService recentPostService) {
        this.recentPostService = recentPostService;
    }

    @GetMapping
    public List<RecentPost> getAllPosts() {
        return recentPostService.getAllPosts();
    }

    @GetMapping("/slug/{slug}")
    public ResponseEntity<RecentPost> getPostBySlug(@PathVariable String slug) {
        return ResponseEntity.ok(recentPostService.getPostBySlug(slug));
    }

    @PostMapping
    public RecentPost createPost(@RequestBody RecentPost post) {
        return recentPostService.createPost(post);
    }

    @PutMapping("/{id}")
    public RecentPost updatePost(@PathVariable Long id, @RequestBody RecentPost post) {
        return recentPostService.updatePost(id, post);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id) {
        recentPostService.deletePost(id);
        return ResponseEntity.noContent().build();
    }
}
