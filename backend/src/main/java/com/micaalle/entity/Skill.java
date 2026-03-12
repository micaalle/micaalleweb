
package com.micaalle.entity;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "skills", uniqueConstraints = @UniqueConstraint(columnNames = {"name"}))
public class Skill {

    public static final String SKILL_NAME_ERROR = "'name' should be between 1-50 characters.";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "name", nullable = false, unique = true)
    @Size(min = 1, max = 50, message = SKILL_NAME_ERROR)
    @NotBlank(message = SKILL_NAME_ERROR)
    private String name;

    @Column(name = "link")
    private String link;

    @Column(name = "icon")
    private Boolean icon = true;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "project_skills",
        joinColumns = @JoinColumn(name = "skill_id"),
        inverseJoinColumns = @JoinColumn(name = "project_id")
    )
    @OrderBy("name ASC")
    @JsonIgnore  
    private Set<Project> projects = new HashSet<>();
}
