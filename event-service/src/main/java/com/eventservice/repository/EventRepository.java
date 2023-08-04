package com.eventservice.repository;

import com.eventservice.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface EventRepository extends JpaRepository<Event, String> {
    @Query("SELECT e FROM Event e WHERE e.isDeleted = false and e.active = true")
    List<Event> findAllActiveRecords();

    @Query("SELECT e FROM Event e WHERE e.id = :eventId AND e.isDeleted = false AND e.active = true")
    Optional<Event> findActiveRecordById(@Param("eventId") String eventId);
}
