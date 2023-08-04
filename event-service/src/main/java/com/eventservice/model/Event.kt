package com.eventservice.model

import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "events")
data class Event(
    val eventName: String,
    val eventDate: LocalDateTime,
    val location: String,
    val description: String,
    val category: String,
    @ManyToOne
    @JoinColumn(name = "organizer_id")
    var organizer: Organizer? = null,
    @ManyToMany
    @JoinTable(
        name = "event_participants",
        joinColumns = [JoinColumn(name = "event_id")],
        inverseJoinColumns = [JoinColumn(name = "participant_id")]
    )
    var participants: MutableList<Participant> = mutableListOf()
) : BaseEntity()