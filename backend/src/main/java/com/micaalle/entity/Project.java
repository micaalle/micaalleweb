package com.micaalle.entity;

import java.util.HashSet;
import java.util.Set;

import com.micaalle.util.DateFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name="projects", indexes = @Index(name = "date_order_ind", columnList = "date_order"))
public class Project {

    public static final String PROJECT_ID_ERROR = "Invalid 'id', project not found.";
    public static final String PROJECT_NAME_ERROR = "'name' should be between 3-30 characters.";
    public static final String PROJECT_DESCRIPTION_ERROR = "'description' should be between 10-250 characters.";
    public static final String PROJECT_START_DATE_ERROR = "'startDate' should be in format 'MM/YYYY'.";
    public static final String PROJECT_END_DATE_ERROR = "'endDate' should be in format 'MM/YYYY'.";
    public static final String PROJECT_LINK_ERROR = "'link' should be between 7-250 characters and start with 'http://' or 'https://'.";
    public static final String PROJECT_PRESENT_ERROR = "'present' should be true or false.";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(nullable = false)
    @Size(min = 3, max = 30, message = PROJECT_NAME_ERROR)
    @NotBlank(message = PROJECT_NAME_ERROR)
    private String name;

    @Column(nullable = false)
    @Size(min = 10, max = 250, message = PROJECT_DESCRIPTION_ERROR)
    @NotBlank(message = PROJECT_DESCRIPTION_ERROR)
    private String description;

    @Column(nullable = false)
    @Pattern(regexp = "^(0[1-9]|1[0-2])/20[0-9]{2}$", message = PROJECT_START_DATE_ERROR)
    @NotBlank(message = PROJECT_START_DATE_ERROR)
    private String startDate;

    @Column(nullable = false)
    @Pattern(regexp = "^(0[1-9]|1[0-2])/20[0-9]{2}$", message = PROJECT_END_DATE_ERROR)
    @NotBlank(message = PROJECT_END_DATE_ERROR)
    private String endDate;

    @Column(nullable = false)
    @NotNull(message = PROJECT_PRESENT_ERROR)
    private Boolean present;

    @Column(nullable = false)
    private String dateOrder;

    @ManyToMany(mappedBy = "projects", fetch = FetchType.LAZY)
    @OrderBy("name ASC")
    private Set<Skill> skills = new HashSet<>();

    @Column(nullable = false)
    @Size(min = 7, max = 250, message = PROJECT_LINK_ERROR)
    @Pattern(regexp = "^(http|https):\\/\\/(.*)$", message = PROJECT_LINK_ERROR)
    @NotBlank(message = PROJECT_LINK_ERROR)
    private String link;

    // -------------------
    // Methods
    // -------------------

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


