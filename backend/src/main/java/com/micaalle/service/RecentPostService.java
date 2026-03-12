package com.micaalle.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.micaalle.entity.RecentPost;
import com.micaalle.repository.RecentPostRepository;

@Service
@Transactional
public class RecentPostService {

    private final RecentPostRepository recentPostRepository;

    public RecentPostService(RecentPostRepository recentPostRepository) {
        this.recentPostRepository = recentPostRepository;
    }

    public List<RecentPost> getAllPosts() {
        return recentPostRepository.findAllByOrderByPublishedAtDesc();
    }

    public List<RecentPost> getRecentPosts(int limit) {
        return recentPostRepository.findTop4ByOrderByPublishedAtDesc().stream().limit(limit).toList();
    }

    public List<RecentPost> getRecentPostsExcluding(String slug, int limit) {
        return getAllPosts().stream()
                .filter(post -> !post.getSlug().equals(slug))
                .limit(limit)
                .toList();
    }

    public RecentPost getPostBySlug(String slug) {
        return recentPostRepository.findBySlug(slug)
                .orElseThrow(() -> new RuntimeException("Post not found for slug: " + slug));
    }

    public RecentPost createPost(RecentPost post) {
        return recentPostRepository.save(post);
    }

    public RecentPost updatePost(Long id, RecentPost postDetails) {
        RecentPost post = recentPostRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Post not found for id: " + id));

        post.setSlug(postDetails.getSlug());
        post.setTitle(postDetails.getTitle());
        post.setPublishedAt(postDetails.getPublishedAt());
        post.setExcerpt(postDetails.getExcerpt());
        post.setContentMarkdown(postDetails.getContentMarkdown());
        post.setCoverImageUrl(postDetails.getCoverImageUrl());

        return recentPostRepository.save(post);
    }

    public void deletePost(Long id) {
        recentPostRepository.deleteById(id);
    }
}
