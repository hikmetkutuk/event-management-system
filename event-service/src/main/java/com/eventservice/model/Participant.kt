package com.eventservice.model

import org.hibernate.annotations.GenericGenerator
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "participants")
data class Participant @JvmOverloads constructor(
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    val id: String? = "",
    val name: String,
    val attendanceStatus: String,
    @ManyToMany(mappedBy = "participants")
    var events: MutableList<Event> = mutableListOf(),
    val active: Boolean,
    val isDeleted: Boolean,
    val insertedTime: LocalDateTime? = null,
    var updatedTime: LocalDateTime? = null
)