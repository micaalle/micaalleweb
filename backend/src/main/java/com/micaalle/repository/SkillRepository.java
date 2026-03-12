package com.micaalle.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.micaalle.entity.Skill;

@Repository
public interface SkillRepository extends JpaRepository<Skill, Integer> {
    Skill findByName(String name);
}
