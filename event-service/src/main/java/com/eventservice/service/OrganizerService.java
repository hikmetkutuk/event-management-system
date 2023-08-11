package com.eventservice.service;

import com.eventservice.dto.OrganizerRequest;
import com.eventservice.dto.OrganizerResponse;
import com.eventservice.exception.CustomExceptionHandler;
import com.eventservice.exception.ResourceNotFoundException;
import com.eventservice.model.Organizer;
import com.eventservice.repository.OrganizerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrganizerService {
    private final OrganizerRepository organizerRepository;
    private static final Logger logger = LoggerFactory.getLogger(OrganizerService.class);

    public OrganizerService(OrganizerRepository organizerRepository) {
        this.organizerRepository = organizerRepository;
    }

    public OrganizerResponse createOrganizer(OrganizerRequest request) {
        try {
            Organizer newOrganizer = new Organizer(
                    null,
                    request.getName(),
                    request.getContactInformation(),
                    null,
                    true,
                    false,
                    LocalDateTime.now(),
                    null
            );
            organizerRepository.save(newOrganizer);

            logger.info("New organizer created with ID: {}", newOrganizer.getId());

            return new OrganizerResponse(
                    newOrganizer.getId(),
                    newOrganizer.getName(),
                    newOrganizer.getContactInformation(),
                    null
            );
        } catch (Exception e) {
            logger.error("Error while creating a new event", e);
            throw new CustomExceptionHandler("Error while creating a new event", e);
        }
    }

    public List<OrganizerResponse> getAllOrganizers() {
        try {
            List<Organizer> organizers = organizerRepository.findAllActiveRecords();

            logger.info("Total {} organizers retrieved", organizers.size());

            return organizers.stream()
                    .map(OrganizerResponse::convert)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            logger.error("Error while retrieving all organizers", e);
            return Collections.emptyList();
        }
    }

    public OrganizerResponse getOrganizerById(String id) {
        try {
            Organizer organizer = findOrganizerById(id);
            logger.info("Organizer with id {} retrieved: {}", id, organizer);
            return new OrganizerResponse(
                    organizer.getId(),
                    organizer.getName(),
                    organizer.getContactInformation(),
                    organizer.getEvents()
            );
        } catch (Exception e) {
            logger.error("Error while retrieving organizer with id {}", id, e);
            throw new CustomExceptionHandler("Error while retrieving organizer", e);
        }
    }

    public OrganizerResponse updateOrganizer(String id, OrganizerRequest request) {
        try {
            Organizer organizer = findOrganizerById(id);
            Organizer updatedOrganizer = new Organizer(
                    organizer.getId(),
                    request.getName(),
                    request.getContactInformation(),
                    organizer.getEvents(),
                    organizer.getActive(),
                    organizer.isDeleted(),
                    organizer.getInsertedTime(),
                    LocalDateTime.now()
            );
            organizerRepository.save(updatedOrganizer);

            logger.info("Organizer with ID {} updated", id);

            return new OrganizerResponse(
                    organizer.getId(),
                    organizer.getName(),
                    organizer.getContactInformation(),
                    organizer.getEvents()
            );
        } catch (Exception e) {
            logger.error("Error while updating the organizer with ID {}", id, e);
            throw new CustomExceptionHandler("Error while updating the organizer", e);
        }
    }

    public String deleteOrganizer(String id) {
        try {
            Organizer organizer = findOrganizerById(id);
            organizer.setActive(false);
            organizer.setDeleted(true);
            organizer.setUpdatedTime(LocalDateTime.now());
            organizerRepository.save(organizer);

            logger.info("Organizer with ID {} deleted", id);

            return "Organizer with ID " + id + " successfully deleted.";
        } catch (Exception e) {
            logger.error("Error while deleting the organizer with ID {}", id, e);
            throw new CustomExceptionHandler("Error while deleting the organizer", e);
        }
    }

    public Organizer findOrganizerById(String id) {
        return organizerRepository.findActiveRecordById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Organizer not found in db " + id));
    }
}
