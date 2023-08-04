package com.eventservice.model

import org.hibernate.annotations.GenericGenerator
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "events")
data class Event @JvmOverloads constructor(
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    val id: String? = "",
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
    var participants: MutableList<Participant>? = mutableListOf(),
    var active: Boolean,
    var isDeleted: Boolean = false,
    val insertedTime: LocalDateTime? = null,
    var updatedTime: LocalDateTime? = null
)