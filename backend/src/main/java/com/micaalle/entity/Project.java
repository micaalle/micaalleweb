package com.micaalle.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.CascadeType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
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
@AllArgsConstructor
@NoArgsConstructor
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

    @Column(name = "content_markdown", columnDefinition = "TEXT")
    @Builder.Default
    private String contentMarkdown = "";

    @Column(name = "what_it_is", columnDefinition = "TEXT")
    private String whatItIs;

    @Column(name = "why_built", columnDefinition = "TEXT")
    private String whyBuilt;

    @Column(name = "challenge_summary", columnDefinition = "TEXT")
    private String challengeSummary;

    @Column(name = "solution_summary", columnDefinition = "TEXT")
    private String solutionSummary;

    @Column(name = "next_steps", columnDefinition = "TEXT")
    private String nextSteps;

    @Column(length = 120)
    private String status;

    @Column(length = 120)
    private String role;

    @Column(name = "cover_image_url", length = 255)
    private String coverImageUrl;

    @Column(name = "repository_url", length = 255)
    private String repositoryUrl;

    @Column(name = "live_url", length = 255)
    private String liveUrl;

    @Column(name = "video_embed_url", length = 255)
    private String videoEmbedUrl;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(nullable = false)
    @Builder.Default
    private Boolean featured = false;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "project_gallery_images", joinColumns = @JoinColumn(name = "project_id"))
    @Column(name = "image_url", nullable = false, length = 255)
    @OrderBy
    @Builder.Default
    private List<String> galleryImageUrls = new ArrayList<>();

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "project_tags", joinColumns = @JoinColumn(name = "project_id"))
    @Column(name = "tag", nullable = false, length = 255)
    @OrderBy
    @Builder.Default
    private List<String> tags = new ArrayList<>();

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @OrderBy("sortOrder ASC")
    @JsonIgnoreProperties("project")
    @Builder.Default
    private List<ProjectSection> projectSections = new ArrayList<>();
}
