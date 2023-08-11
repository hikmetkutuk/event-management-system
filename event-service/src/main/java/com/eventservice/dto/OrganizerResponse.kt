package com.eventservice.dto

import com.eventservice.model.Event
import com.eventservice.model.Organizer

data class OrganizerResponse(
    val id: String?,
    val name: String,
    val contactInformation: String,
    val events: List<Event>?
) {
    companion object {
        @JvmStatic
        fun convert(from: Organizer): OrganizerResponse {
            val events = from.events?.filter { it.active && !it.isDeleted }
            return OrganizerResponse(
                from.id,
                from.name,
                from.contactInformation,
                events
            )
        }
    }
}