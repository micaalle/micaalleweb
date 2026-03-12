package com.micaalle.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.micaalle.entity.Project;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

    @Override
    @EntityGraph(attributePaths = {"tags", "galleryImageUrls", "projectSections", "projectSections.mediaItems"})
    List<Project> findAll();

    @EntityGraph(attributePaths = {"tags", "galleryImageUrls", "projectSections", "projectSections.mediaItems"})
    Optional<Project> findBySlug(String slug);

    List<Project> findAllByOrderByFeaturedDescStartDateDescIdDesc();
}
