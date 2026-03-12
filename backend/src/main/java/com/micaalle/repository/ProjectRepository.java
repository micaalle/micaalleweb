package com.micaalle.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.micaalle.entity.Project;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
    List<Project> findAllByOrderByFeaturedDescStartDateDesc();
    List<Project> findTop3ByOrderByFeaturedDescStartDateDesc();
    Optional<Project> findBySlug(String slug);
}
