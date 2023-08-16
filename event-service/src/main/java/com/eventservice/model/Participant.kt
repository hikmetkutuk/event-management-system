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
    @Column(nullable = false)
    val name: String,
    @Column(nullable = false, unique = true)
    val email: String,
    @Column(nullable = false)
    val attendanceStatus: String,
    @ManyToMany(mappedBy = "participants")
    var events: MutableList<Event>? = mutableListOf(),
    var active: Boolean,
    var isDeleted: Boolean,
    val insertedTime: LocalDateTime? = null,
    var updatedTime: LocalDateTime? = null
)