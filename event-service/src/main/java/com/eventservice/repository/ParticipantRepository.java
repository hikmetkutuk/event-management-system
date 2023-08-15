package com.eventservice.repository;

import com.eventservice.model.Participant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ParticipantRepository extends JpaRepository<Participant, String> {
    @Query("SELECT p FROM Participant p WHERE p.isDeleted = false and p.active = true")
    List<Participant> findAllActiveRecords();

    @Query("SELECT p FROM Participant p WHERE p.id = :participantId AND p.isDeleted = false AND p.active = true")
    Optional<Participant> findActiveRecordById(@Param("participantId") String participantId);
}
