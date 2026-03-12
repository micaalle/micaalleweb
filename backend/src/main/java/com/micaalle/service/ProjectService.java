package com.micaalle.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.micaalle.entity.Project;
import com.micaalle.entity.ProjectMedia;
import com.micaalle.entity.ProjectSection;
import com.micaalle.repository.ProjectRepository;

@Service
@Transactional
public class ProjectService {

    private final ProjectRepository projectRepository;

    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public List<Project> getAllProjects() {
        return projectRepository.findAllByOrderByFeaturedDescStartDateDesc();
    }

    public List<Project> getFeaturedProjects(int limit) {
        List<Project> featured = projectRepository.findTop3ByOrderByFeaturedDescStartDateDesc();
        return featured.stream().limit(limit).toList();
    }

    public Project getProjectBySlug(String slug) {
        return projectRepository.findBySlug(slug)
                .orElseThrow(() -> new RuntimeException("Project not found for slug: " + slug));
    }

    public List<Project> getRelatedProjects(String slug, int limit) {
        return getAllProjects().stream()
                .filter(project -> !project.getSlug().equals(slug))
                .limit(limit)
                .toList();
    }

    public Project createProject(Project project) {
        normalizeRelationships(project);
        return projectRepository.save(project);
    }

    public Project updateProject(Long id, Project projectDetails) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Project not found for id: " + id));

        project.setSlug(projectDetails.getSlug());
        project.setTitle(projectDetails.getTitle());
        project.setSummary(projectDetails.getSummary());
        project.setContentMarkdown(projectDetails.getContentMarkdown());
        project.setWhatItIs(projectDetails.getWhatItIs());
        project.setWhyBuilt(projectDetails.getWhyBuilt());
        project.setChallengeSummary(projectDetails.getChallengeSummary());
        project.setSolutionSummary(projectDetails.getSolutionSummary());
        project.setNextSteps(projectDetails.getNextSteps());
        project.setStatus(projectDetails.getStatus());
        project.setRole(projectDetails.getRole());
        project.setCoverImageUrl(projectDetails.getCoverImageUrl());
        project.setRepositoryUrl(projectDetails.getRepositoryUrl());
        project.setLiveUrl(projectDetails.getLiveUrl());
        project.setVideoEmbedUrl(projectDetails.getVideoEmbedUrl());
        project.setGalleryImageUrls(projectDetails.getGalleryImageUrls());
        project.setProjectSections(projectDetails.getProjectSections());
        project.setStartDate(projectDetails.getStartDate());
        project.setEndDate(projectDetails.getEndDate());
        project.setFeatured(projectDetails.getFeatured());
        project.setTags(projectDetails.getTags());

        normalizeRelationships(project);
        return projectRepository.save(project);
    }

    public void deleteProject(Long id) {
        projectRepository.deleteById(id);
    }

    private void normalizeRelationships(Project project) {
        if (project.getProjectSections() == null) {
            return;
        }

        for (ProjectSection section : project.getProjectSections()) {
            section.setProject(project);
            if (section.getMediaItems() == null) {
                continue;
            }
            for (ProjectMedia media : section.getMediaItems()) {
                media.setProject(project);
                media.setSection(section);
            }
        }
    }
}
