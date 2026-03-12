package com.micaalle.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.micaalle.entity.PhotoEntry;
import com.micaalle.service.PhotoEntryService;

@RestController
@RequestMapping("/api/photos")
@CrossOrigin(origins = "*")
public class PhotoEntryController {

    private final PhotoEntryService photoEntryService;

    public PhotoEntryController(PhotoEntryService photoEntryService) {
        this.photoEntryService = photoEntryService;
    }

    @GetMapping
    public List<PhotoEntry> getAllPhotos() {
        return photoEntryService.getAllPhotos();
    }

    @PostMapping
    public PhotoEntry createPhoto(@RequestBody PhotoEntry photoEntry) {
        return photoEntryService.createPhoto(photoEntry);
    }

    @PutMapping("/{id}")
    public PhotoEntry updatePhoto(@PathVariable Long id, @RequestBody PhotoEntry photoEntry) {
        return photoEntryService.updatePhoto(id, photoEntry);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePhoto(@PathVariable Long id) {
        photoEntryService.deletePhoto(id);
        return ResponseEntity.noContent().build();
    }
}
