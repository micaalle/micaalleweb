package com.micaalle.entity;

import java.util.HashSet;
import java.util.Set;

import com.micaalle.util.DateFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OrderBy;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name="projects", indexes = @Index(name = "date_order_ind", columnList = "date_order"))
public class Project {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(nullable = false)
    @Size(min = 3, max = 30)
    @NotBlank
    private String name;

    @Column(nullable = false)
    @Size(min = 10, max = 250)
    @NotBlank
    private String description;

    @Column(nullable = false)
    @Pattern(regexp = "^(0[1-9]|1[0-2])/20[0-9]{2}$")
    @NotBlank
    private String startDate;

    @Column(nullable = false)
    @Pattern(regexp = "^(0[1-9]|1[0-2])/20[0-9]{2}$")
    @NotBlank
    private String endDate;

    @Column(nullable = false)
    @NotNull
    private Boolean present;

    @Column(nullable = false)
    private String dateOrder;

    @ManyToMany(mappedBy = "projects", fetch = FetchType.LAZY)
    @OrderBy("name ASC")
    private Set<Skill> skills = new HashSet<>();

    @Column(nullable = false)
    @Size(min = 7, max = 250)
    @Pattern(regexp = "^(http|https):\\/\\/(.*)$")
    @NotBlank
    private String link;

    public void createOrder() {
        String[] startSplit = this.startDate.split("/", 2);
        String[] endSplit = this.endDate.split("/", 2);
        this.dateOrder = endSplit[1] + endSplit[0] + (present ? "1" : "0") + startSplit[1] + startSplit[0];
    }

    public boolean syncEndDate() {
        boolean changed = false;
        if (present) {
            changed = !this.endDate.equals(DateFormat.MMyyyy());
            this.endDate = DateFormat.MMyyyy();
            createOrder();
        }
        return changed;
    }

    public void addSkill(Skill skill) {
        skill.getProjects().add(this);
        this.skills.add(skill);
    }

    public void deleteSkill(Skill skill) {
        skill.getProjects().remove(this);
        this.skills.remove(skill);
    }
}

