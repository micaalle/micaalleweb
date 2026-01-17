package com.micaalle.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.micaalle.entity.Skill;
import com.micaalle.repository.SkillRepository;

@Service
@Transactional
public class SkillService {

    private final SkillRepository skillRepository;

    public SkillService(SkillRepository skillRepository) {
        this.skillRepository = skillRepository;
    }

    public List<Skill> getAllSkills() {
        return skillRepository.findAll();
    }

    public Skill getSkillByName(String name) {
        return skillRepository.findByName(name);
    }

    public Skill createSkill(Skill skill) {
        return skillRepository.save(skill);
    }
}
