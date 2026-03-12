package com.micaalle.service;

import java.util.Comparator;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.micaalle.entity.Project;
import com.micaalle.entity.ProjectMedia;
import com.micaalle.entity.ProjectSection;
import com.micaalle.repository.ProjectRepository;

@Service
@Transactional(readOnly = true)
public class ProjectService {

    private final ProjectRepository projectRepository;

    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public List<Project> getAllProjects() {
        List<Project> projects = projectRepository.findAllByOrderByFeaturedDescStartDateDescIdDesc();
        projects.forEach(this::normalizeProject);
        return projects;
    }

    public Project getProjectBySlug(String slug) {
        Project project = projectRepository.findBySlug(slug)
                .orElseThrow(() -> new RuntimeException("Project not found."));
        normalizeProject(project);
        return project;
    }

    private void normalizeProject(Project project) {
        if (project.getProjectSections() == null) {
            return;
        }
        project.getProjectSections().sort(Comparator.comparing(ProjectSection::getSortOrder));
        for (ProjectSection section : project.getProjectSections()) {
            if (section.getMediaItems() == null) {
                continue;
            }
            section.getMediaItems().sort(Comparator.comparing(ProjectMedia::getSortOrder));
        }
    }
}
