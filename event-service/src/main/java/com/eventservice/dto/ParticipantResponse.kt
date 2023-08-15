package com.eventservice.dto

import com.eventservice.model.Event
import com.eventservice.model.Participant

data class ParticipantResponse(
    val id: String?,
    val name: String,
    val email: String,
    val attendanceStatus: String,
    var events: List<Event>?,
) {
    companion object {
        @JvmStatic
        fun convert(from: Participant): ParticipantResponse {
            val events = from.events?.filter { it.active && !it.isDeleted }
            return ParticipantResponse(
                from.id,
                from.name,
                from.email,
                from.attendanceStatus,
                events
            )
        }
    }
}