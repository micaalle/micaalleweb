package com.micaalle.controller;

import static org.hamcrest.Matchers.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

import com.micaalle.dto.ProjectDTO;
import com.micaalle.dto.SkillDTO;
import com.micaalle.service.ProjectService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@WebMvcTest(ProjectController.class)
public class ProjectControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProjectService projectService;

    private ProjectDTO project;
    private SkillDTO skill;

    @BeforeEach
    public void setUp() {
        skill = SkillDTO.builder()
                .id(1)
                .name("JavaScript")
                .link("https://www.javascript.com/")
                .icon(true)
                .build();

        project = ProjectDTO.builder()
                .id(1)
                .name("My Portfolio")
                .description("Personal portfolio website showcasing projects and skills.")
                .startDate("01/2026")
                .endDate(null)
                .present(true)
                .link("https://github.com/micaalleweb/micaalle.com")
                .skills(List.of(skill))
                .build();
    }

    @Test
    public void testGetProjects() throws Exception {
        // Mock the service to return our project list
        given(projectService.getProjectDTOs()).willReturn(List.of(project));

        mockMvc.perform(MockMvcRequestBuilders.get("/projects/get"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.projects", hasSize(1)))
                .andExpect(jsonPath("$.projects[0].id", is(project.getId())))
                .andExpect(jsonPath("$.projects[0].name", is(project.getName())))
                .andExpect(jsonPath("$.projects[0].description", is(project.getDescription())))
                .andExpect(jsonPath("$.projects[0].startDate", is(project.getStartDate())))
                .andExpect(jsonPath("$.projects[0].present", is(project.getPresent())))
                .andExpect(jsonPath("$.projects[0].skills", hasSize(1)))
                .andExpect(jsonPath("$.projects[0].skills[0].id", is(skill.getId())))
                .andExpect(jsonPath("$.projects[0].skills[0].name", is(skill.getName())))
                .andExpect(jsonPath("$.projects[0].skills[0].link", is(skill.getLink())))
                .andExpect(jsonPath("$.projects[0].skills[0].icon", is(skill.getIcon())))
                .andExpect(jsonPath("$.projects[0].link", is(project.getLink())));
    }
}
