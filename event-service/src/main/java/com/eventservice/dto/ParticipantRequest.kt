package com.eventservice.dto

data class ParticipantRequest (
    val name: String,
    val email: String,
    val attendanceStatus: String,
    val eventId: String
)