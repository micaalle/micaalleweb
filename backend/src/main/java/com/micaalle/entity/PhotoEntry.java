package com.micaalle.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@Table(name = "photo_entries")
public class PhotoEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 140)
    private String title;

    @Column(length = 255)
    private String caption;

    @Column(nullable = false, length = 255)
    private String imageUrl;

    @Column(nullable = false)
    private LocalDate takenOn;

    @Column(length = 180)
    private String locationName;

    @Column(nullable = false)
    @Builder.Default
    private Integer sortOrder = 0;
}
