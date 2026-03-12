package com.micaalle.config;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;
import org.springframework.web.servlet.resource.ResourceResolverChain;

import jakarta.servlet.http.HttpServletRequest;

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
                .setCachePeriod(3600)
                .resourceChain(true)
                .addResolver(new MediaFallbackResolver());
    }

    private static class MediaFallbackResolver extends PathResourceResolver {

        private static final List<String> EXTENSIONS = List.of(".jpg", ".jpeg", ".png", ".webp");

        @Override
        protected Resource resolveResourceInternal(
                HttpServletRequest request,
                String requestPath,
                List<? extends Resource> locations,
                ResourceResolverChain chain) {

            Resource resource = super.resolveResourceInternal(request, requestPath, locations, chain);
            if (resource != null) {
                return resource;
            }

            String basePath = stripExtension(requestPath);

            for (String extension : EXTENSIONS) {
                String candidate = basePath + extension;
                Resource fallback = super.resolveResourceInternal(request, candidate, locations, chain);
                if (fallback != null) {
                    return fallback;
                }
            }

            return null;
        }

        @Override
        protected String resolveUrlPathInternal(
                String resourcePath,
                List<? extends Resource> locations,
                ResourceResolverChain chain) {

            String resolved = super.resolveUrlPathInternal(resourcePath, locations, chain);
            if (resolved != null) {
                return resolved;
            }

            String basePath = stripExtension(resourcePath);

            for (String extension : EXTENSIONS) {
                String candidate = basePath + extension;
                String fallback = super.resolveUrlPathInternal(candidate, locations, chain);
                if (fallback != null) {
                    return fallback;
                }
            }

            return null;
        }

        private String stripExtension(String path) {
            int slash = path.lastIndexOf('/');
            int dot = path.lastIndexOf('.');

            if (dot > slash) {
                return path.substring(0, dot);
            }

            return path;
        }
    }
}
