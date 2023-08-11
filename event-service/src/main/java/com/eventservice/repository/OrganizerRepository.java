package com.eventservice.repository;

import com.eventservice.model.Event;
import com.eventservice.model.Organizer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface OrganizerRepository extends JpaRepository<Organizer, String> {
    @Query("SELECT o FROM Organizer o WHERE o.isDeleted = false and o.active = true")
    List<Organizer> findAllActiveRecords();

    @Query("SELECT o FROM Organizer o WHERE o.id = :organizerId AND o.isDeleted = false AND o.active = true")
    Optional<Organizer> findActiveRecordById(@Param("organizerId") String organizerId);
}
