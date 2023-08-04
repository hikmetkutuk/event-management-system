package com.eventservice.dto

import com.eventservice.model.Event
import java.time.LocalDateTime

data class EventResponse(
    val id: String?,
    val eventName: String,
    val eventDate: LocalDateTime,
    val location: String,
    val description: String,
    val category: String,
) {
    companion object {
        @JvmStatic
        fun convert(from: Event): EventResponse {
            return EventResponse(
                from.id,
                from.eventName,
                from.eventDate,
                from.location,
                from.description,
                from.category
            )
        }
    }
}