package com.eventservice.controller;

import com.eventservice.dto.OrganizerRequest;
import com.eventservice.dto.OrganizerResponse;
import com.eventservice.service.OrganizerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/api/organizer")
public class OrganizerController {

    private final OrganizerService organizerService;

    public OrganizerController(OrganizerService organizerService) {
        this.organizerService = organizerService;
    }

    @PostMapping("/create")
    public ResponseEntity<OrganizerResponse> createOrganizer(@RequestBody OrganizerRequest request) {
        return new ResponseEntity<>(organizerService.createOrganizer(request), HttpStatus.CREATED);
    }

    @GetMapping("/list")
    public ResponseEntity<List<OrganizerResponse>> getAllOrganizers() {
        return new ResponseEntity<>(organizerService.getAllOrganizers(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrganizerResponse> getOrganizerById(@PathVariable String id) {
        return new ResponseEntity<>(organizerService.getOrganizerById(id), HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<OrganizerResponse> updateOrganizer(@PathVariable String id, @RequestBody OrganizerRequest request) {
        return new ResponseEntity<>(organizerService.updateOrganizer(id, request), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteOrganizer(@PathVariable String id) {
        return new ResponseEntity<>(organizerService.deleteOrganizer(id), HttpStatus.OK);
    }
}
