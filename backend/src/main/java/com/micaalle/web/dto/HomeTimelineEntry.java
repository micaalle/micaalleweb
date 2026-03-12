package com.micaalle.web.dto;

public record HomeTimelineEntry(
        String category,
        String period,
        String title,
        String subtitle,
        String description) {
}
