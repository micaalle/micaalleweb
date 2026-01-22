package com.micaalle.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.micaalle.entity.Project;
import com.micaalle.repository.ProjectRepository;

@Service
@Transactional
public class ProjectService {

    private final ProjectRepository projectRepository;

    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public List<Project> getAllProjects() {
        return projectRepository.findAllOrderedByDate();
    }

    public Optional<Project> getProjectById(Integer id) {
        return projectRepository.findById(id);
    }

    public Project createProject(Project project) {
        project.createOrder();

        // Ensure bidirectional link for skills
        if (project.getSkills() != null) {
            project.getSkills().forEach(skill -> skill.getProjects().add(project));
        }

        return projectRepository.save(project);
    }

    public Project updateProject(Integer id, Project projectDetails) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(Project.PROJECT_ID_ERROR));

        project.setName(projectDetails.getName());
        project.setDescription(projectDetails.getDescription());
        project.setStartDate(projectDetails.getStartDate());
        project.setEndDate(projectDetails.getEndDate());
        project.setPresent(projectDetails.getPresent());
        project.setLink(projectDetails.getLink());
        project.createOrder();

        return projectRepository.save(project);
    }

    public void deleteProject(Integer id) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(Project.PROJECT_ID_ERROR));
        projectRepository.delete(project);
    }
}

