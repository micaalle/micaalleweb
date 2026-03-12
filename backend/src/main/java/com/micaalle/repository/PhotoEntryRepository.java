package com.micaalle.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.micaalle.entity.PhotoEntry;

@Repository
public interface PhotoEntryRepository extends JpaRepository<PhotoEntry, Long> {
    List<PhotoEntry> findAllByOrderByTakenOnDescSortOrderAscIdAsc();
}
