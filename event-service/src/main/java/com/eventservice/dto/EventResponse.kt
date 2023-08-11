package com.eventservice.dto

import com.eventservice.model.Event
import com.eventservice.model.Organizer
import java.time.LocalDateTime

data class EventResponse(
    val id: String?,
    val eventName: String,
    val eventDate: LocalDateTime,
    val location: String,
    val description: String,
    val category: String,
    val organizer: Organizer?
) {
    companion object {
        @JvmStatic
        fun convert(from: Event): EventResponse {
            val organizer = if (from.organizer?.active == true && from.organizer?.isDeleted == false) from.organizer else null
            return EventResponse(
                from.id,
                from.eventName,
                from.eventDate,
                from.location,
                from.description,
                from.category,
                organizer
            )
        }
    }
}