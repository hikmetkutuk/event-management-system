package com.eventservice.controller;

import com.eventservice.dto.ParticipantRequest;
import com.eventservice.dto.ParticipantResponse;
import com.eventservice.service.ParticipantService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/api/participant")
public class ParticipantController {
    private final ParticipantService participantService;

    public ParticipantController(ParticipantService participantService) {
        this.participantService = participantService;
    }

    @PostMapping("/create")
    public ResponseEntity<ParticipantResponse> createParticipant(@RequestBody ParticipantRequest request) {
        return new ResponseEntity<>(participantService.createParticipant(request), HttpStatus.CREATED);
    }

    @GetMapping("/list")
    public ResponseEntity<List<ParticipantResponse>> getAllParticipants() {
        return new ResponseEntity<>(participantService.getAllParticipants(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ParticipantResponse> getParticipantById(@PathVariable String id) {
        return new ResponseEntity<>(participantService.getParticipantById(id), HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ParticipantResponse> updateParticipant(@PathVariable String id, @RequestBody ParticipantRequest request) {
        return new ResponseEntity<>(participantService.updateParticipant(id, request), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteParticipant(@PathVariable String id) {
        return new ResponseEntity<>(participantService.deleteParticipant(id), HttpStatus.OK);
    }
}
