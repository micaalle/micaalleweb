package com.micaalle.config;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MediaResourceConfig implements WebMvcConfigurer {

    @Value("${app.media-root:${user.home}/portfolio-media}")
    private String mediaRoot;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        Path root = Paths.get(mediaRoot).toAbsolutePath().normalize();
        String location = root.toUri().toString();

        if (!location.endsWith("/")) {
            location += "/";
        }

        registry.addResourceHandler("/media/**")
                .addResourceLocations(location)
                .setCachePeriod(3600);
    }
}
