package com.eventservice.model

import org.hibernate.annotations.GenericGenerator
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "organizers")
data class Organizer @JvmOverloads constructor(
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    val id: String? = "",
    val name: String,
    val contactInformation: String,
    @OneToMany(mappedBy = "organizer")
    var events: MutableList<Event> = mutableListOf(),
    val active: Boolean,
    val isDeleted: Boolean,
    val insertedTime: LocalDateTime? = null,
    var updatedTime: LocalDateTime? = null
)