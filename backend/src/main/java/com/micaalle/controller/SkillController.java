package com.micaalle.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.micaalle.entity.Skill;
import com.micaalle.service.SkillService;

@RestController
@RequestMapping("/api/skills")
@CrossOrigin(origins = "*")
public class SkillController {

    private final SkillService skillService;

    public SkillController(SkillService skillService) {
        this.skillService = skillService;
    }

    @GetMapping
    public List<Skill> getAllSkills() {
        return skillService.getAllSkills();
    }

    @GetMapping("/{name}")
    public Skill getSkillByName(@PathVariable String name) {
        return skillService.getSkillByName(name);
    }

    @PostMapping
    public Skill createSkill(@RequestBody Skill skill) {
        return skillService.createSkill(skill);
    }
}
