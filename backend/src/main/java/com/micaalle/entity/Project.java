package com.micaalle.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "projects")
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 120)
    private String slug;

    @Column(nullable = false, length = 120)
    private String title;

    @Column(nullable = false, length = 220)
    private String summary;

    @Lob
    @Column(nullable = false, columnDefinition = "TEXT")
    private String contentMarkdown;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String whatItIs;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String whyBuilt;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String challengeSummary;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String solutionSummary;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String nextSteps;

    @Column(length = 120)
    private String status;

    @Column(length = 120)
    private String role;

    @Column(length = 255)
    private String coverImageUrl;

    @Column(length = 255)
    private String repositoryUrl;

    @Column(length = 255)
    private String liveUrl;

    @Column(length = 255)
    private String videoEmbedUrl;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "project_gallery_images", joinColumns = @JoinColumn(name = "project_id"))
    @Column(name = "image_url", nullable = false)
    @Fetch(FetchMode.SUBSELECT)
    @Builder.Default
    private List<String> galleryImageUrls = new ArrayList<>();

    @OneToMany(mappedBy = "project", fetch = FetchType.EAGER, cascade = jakarta.persistence.CascadeType.ALL, orphanRemoval = true)
    @OrderBy("sortOrder ASC, id ASC")
    @Fetch(FetchMode.SUBSELECT)
    @Builder.Default
    private List<ProjectSection> projectSections = new ArrayList<>();

    @Column(nullable = false)
    private LocalDate startDate;

    private LocalDate endDate;

    @Column(nullable = false)
    @Builder.Default
    private Boolean featured = Boolean.FALSE;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "project_tags", joinColumns = @JoinColumn(name = "project_id"))
    @Column(name = "tag", nullable = false)
    @Fetch(FetchMode.SUBSELECT)
    @Builder.Default
    private List<String> tags = new ArrayList<>();
}
