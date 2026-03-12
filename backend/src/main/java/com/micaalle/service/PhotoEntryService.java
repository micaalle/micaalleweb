package com.micaalle.service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.micaalle.entity.PhotoEntry;
import com.micaalle.repository.PhotoEntryRepository;

@Service
@Transactional
public class PhotoEntryService {

    private final PhotoEntryRepository photoEntryRepository;

    public PhotoEntryService(PhotoEntryRepository photoEntryRepository) {
        this.photoEntryRepository = photoEntryRepository;
    }

    public List<PhotoEntry> getAllPhotos() {
        return photoEntryRepository.findAllByOrderByTakenOnDescSortOrderAscIdAsc();
    }

    public Map<Integer, List<PhotoEntry>> getPhotosGroupedByYear() {
        Map<Integer, List<PhotoEntry>> grouped = new LinkedHashMap<>();
        for (PhotoEntry photo : getAllPhotos()) {
            grouped.computeIfAbsent(photo.getTakenOn().getYear(), ignored -> new java.util.ArrayList<>()).add(photo);
        }
        return grouped;
    }

    public PhotoEntry getPhotoById(Long id) {
        return photoEntryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Photo not found for id: " + id));
    }

    public PhotoEntry createPhoto(PhotoEntry photoEntry) {
        return photoEntryRepository.save(photoEntry);
    }

    public PhotoEntry updatePhoto(Long id, PhotoEntry photoDetails) {
        PhotoEntry photo = photoEntryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Photo not found for id: " + id));

        photo.setTitle(photoDetails.getTitle());
        photo.setCaption(photoDetails.getCaption());
        photo.setImageUrl(photoDetails.getImageUrl());
        photo.setTakenOn(photoDetails.getTakenOn());
        photo.setLocationName(photoDetails.getLocationName());
        photo.setSortOrder(photoDetails.getSortOrder());

        return photoEntryRepository.save(photo);
    }

    public void deletePhoto(Long id) {
        photoEntryRepository.deleteById(id);
    }
}
