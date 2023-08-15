package com.eventservice.service;

import com.eventservice.dto.ParticipantRequest;
import com.eventservice.dto.ParticipantResponse;
import com.eventservice.exception.CustomExceptionHandler;
import com.eventservice.exception.ResourceNotFoundException;
import com.eventservice.model.Participant;
import com.eventservice.repository.ParticipantRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ParticipantService {
    private final ParticipantRepository participantRepository;
    private final EventService eventService;
    private static final Logger logger = LoggerFactory.getLogger(ParticipantService.class);

    public ParticipantService(ParticipantRepository participantRepository, EventService eventService) {
        this.participantRepository = participantRepository;
        this.eventService = eventService;
    }

    public ParticipantResponse createParticipant(ParticipantRequest request) {
        try {
            Participant newParticipant = new Participant(
                    null,
                    request.getName(),
                    request.getEmail(),
                    request.getAttendanceStatus(),
                    null,
                    true,
                    false,
                    LocalDateTime.now(),
                    null
            );
            participantRepository.save(newParticipant);

            logger.info("New participant created with ID: {}", newParticipant.getId());

            return new ParticipantResponse(
                    newParticipant.getId(),
                    newParticipant.getName(),
                    newParticipant.getEmail(),
                    newParticipant.getAttendanceStatus(),
                    null
            );
        } catch (Exception e) {
            logger.error("Error while creating a new participant", e);
            throw new CustomExceptionHandler("Error while creating a new participant", e);
        }
    }

    public List<ParticipantResponse> getAllParticipants() {
        try {
            List<Participant> participants = participantRepository.findAllActiveRecords();

            logger.info("Total {} participants retrieved", participants.size());

            return participants.stream()
                    .map(ParticipantResponse::convert)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            logger.error("Error while retrieving all participants", e);
            return Collections.emptyList();
        }
    }

    public ParticipantResponse getParticipantById(String id) {
        try {
            Participant participant = findParticipantById(id);
            logger.info("Participant with id {} retrieved: {}", id, participant);
            return new ParticipantResponse(
                    participant.getId(),
                    participant.getName(),
                    participant.getEmail(),
                    participant.getAttendanceStatus(),
                    participant.getEvents()
            );
        } catch (Exception e) {
            logger.error("Error while retrieving participant with id {}", id, e);
            throw new CustomExceptionHandler("Error while retrieving participant", e);
        }
    }

    private Participant findParticipantById(String id) {
        return participantRepository.findActiveRecordById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Participant not found in db " + id));
    }
}
