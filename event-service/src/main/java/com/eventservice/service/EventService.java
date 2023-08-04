package com.eventservice.service;

import com.eventservice.dto.EventRequest;
import com.eventservice.dto.EventResponse;
import com.eventservice.exception.ResourceNotFoundException;
import com.eventservice.model.Event;
import com.eventservice.repository.EventRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EventService {
    private final EventRepository eventRepository;

    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public List<EventResponse> getAllEvents() {
        return eventRepository.findAllActiveRecords()
                .stream()
                .map(EventResponse::convert)
                .collect(Collectors.toList());
    }

    public EventResponse getEventById(String id) {
        Event event = findEventById(id);
        return new EventResponse(
                event.getId(),
                event.getEventName(),
                event.getEventDate(),
                event.getLocation(),
                event.getDescription(),
                event.getCategory()
        );
    }

    public EventResponse createEvent(EventRequest request) {
        Event newEvent = new Event(
                null,
                request.getEventName(),
                request.getEventDate(),
                request.getLocation(),
                request.getDescription(),
                request.getCategory(),
                null,
                null,
                true,
                false,
                LocalDateTime.now(),
                null
        );
        eventRepository.save(newEvent);
        return new EventResponse(
                newEvent.getId(),
                newEvent.getEventName(),
                newEvent.getEventDate(),
                newEvent.getLocation(),
                newEvent.getDescription(),
                newEvent.getCategory()
        );
    }

    public EventResponse updateEvent(String id, EventRequest request) {
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
        return new EventResponse(
                event.getId(),
                request.getEventName(),
                request.getEventDate(),
                request.getLocation(),
                request.getDescription(),
                request.getCategory()
        );
    }

    public String deleteEvent(String id) {
        Event event = findEventById(id);
        event.setActive(false);
        event.setDeleted(true);
        event.setUpdatedTime(LocalDateTime.now());
        eventRepository.save(event);
        return "Event with ID " + id + " successfully deleted.";
    }

    private Event findEventById(String id) {
        return eventRepository.findActiveRecordById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Event not found in db " + id));
    }
}
