package com.eventservice.dto

import java.time.LocalDateTime

data class EventRequest(
    val eventName: String,
    val eventDate: LocalDateTime,
    val location: String,
    val description: String,
    val category: String,
    val organizerId: String
)