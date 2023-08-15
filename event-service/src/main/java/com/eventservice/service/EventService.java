package com.eventservice.service;

import com.eventservice.dto.EventRequest;
import com.eventservice.dto.EventResponse;
import com.eventservice.exception.CustomExceptionHandler;
import com.eventservice.exception.ResourceNotFoundException;
import com.eventservice.model.Event;
import com.eventservice.model.Organizer;
import com.eventservice.repository.EventRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EventService {
    private final EventRepository eventRepository;
    private final OrganizerService organizerService;
    private static final Logger logger = LoggerFactory.getLogger(EventService.class);

    public EventService(EventRepository eventRepository, OrganizerService organizerService) {
        this.eventRepository = eventRepository;
        this.organizerService = organizerService;
    }

    public List<EventResponse> getAllEvents() {
        try {
            List<Event> events = findAllEvents();

            logger.info("Total {} events retrieved", events.size());

            return events.stream()
                    .map(EventResponse::convert)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            logger.error("Error while retrieving all events", e);
            return Collections.emptyList();
        }
    }

    public EventResponse getEventById(String id) {
        try {
            Event event = findEventById(id);
            logger.info("Event with id {} retrieved: {}", id, event);
            return new EventResponse(
                    event.getId(),
                    event.getEventName(),
                    event.getEventDate(),
                    event.getLocation(),
                    event.getDescription(),
                    event.getCategory(),
                    event.getOrganizer()
            );
        } catch (Exception e) {
            logger.error("Error while retrieving event with id {}", id, e);
            throw new CustomExceptionHandler("Error while retrieving event", e);
        }
    }

    public EventResponse createEvent(EventRequest request) {
        try {
            Organizer organizer = organizerService.findOrganizerById(request.getOrganizerId());
            Event newEvent = new Event(
                    null,
                    request.getEventName(),
                    request.getEventDate(),
                    request.getLocation(),
                    request.getDescription(),
                    request.getCategory(),
                    organizer,
                    null,
                    true,
                    false,
                    LocalDateTime.now(),
                    null
            );
            eventRepository.save(newEvent);

            logger.info("New event created with ID: {}", newEvent.getId());

            return new EventResponse(
                    newEvent.getId(),
                    newEvent.getEventName(),
                    newEvent.getEventDate(),
                    newEvent.getLocation(),
                    newEvent.getDescription(),
                    newEvent.getCategory(),
                    organizer
            );
        } catch (Exception e) {
            logger.error("Error while creating a new event", e);
            throw new CustomExceptionHandler("Error while creating a new event", e);
        }
    }

    public EventResponse updateEvent(String id, EventRequest request) {
        try {
            Event event = findEventById(id);
            Event updatedEvent = new Event(
                    event.getId(),
                    request.getEventName(),
                    request.getEventDate(),
                    request.getLocation(),
                    request.getDescription(),
                    request.getCategory(),
                    event.getOrganizer(),
                    event.getParticipants(),
                    true,
                    false,
                    event.getInsertedTime(),
                    LocalDateTime.now()
            );
            eventRepository.save(updatedEvent);

            logger.info("Event with ID {} updated", id);

            return new EventResponse(
                    event.getId(),
                    request.getEventName(),
                    request.getEventDate(),
                    request.getLocation(),
                    request.getDescription(),
                    request.getCategory(),
                    event.getOrganizer()
            );
        } catch (Exception e) {
            logger.error("Error while updating the event with ID {}", id, e);
            throw new CustomExceptionHandler("Error while updating the event", e);
        }
    }

    public String deleteEvent(String id) {
        try {
            Event event = findEventById(id);
            event.setActive(false);
            event.setDeleted(true);
            event.setUpdatedTime(LocalDateTime.now());
            eventRepository.save(event);

            logger.info("Event with ID {} deleted", id);

            return "Event with ID " + id + " successfully deleted.";
        } catch (Exception e) {
            logger.error("Error while deleting the event with ID {}", id, e);

            throw new CustomExceptionHandler("Error while deleting the event", e);
        }
    }

    public Event findEventById(String id) {
        return eventRepository.findActiveRecordById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Event not found in db " + id));
    }

    public List<Event> findAllEvents() {
        return eventRepository.findAllActiveRecords();
    }
}
