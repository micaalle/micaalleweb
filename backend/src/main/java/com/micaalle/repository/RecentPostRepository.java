package com.micaalle.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.micaalle.entity.RecentPost;

@Repository
public interface RecentPostRepository extends JpaRepository<RecentPost, Long> {
    List<RecentPost> findAllByOrderByPublishedAtDesc();
    List<RecentPost> findTop4ByOrderByPublishedAtDesc();
    Optional<RecentPost> findBySlug(String slug);
}
