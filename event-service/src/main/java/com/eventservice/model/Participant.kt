package com.eventservice.model

import javax.persistence.Entity
import javax.persistence.ManyToMany
import javax.persistence.Table

@Entity
@Table(name = "participants")
data class Participant(
    val name: String,
    val attendanceStatus: String,
    @ManyToMany(mappedBy = "participants")
    var events: MutableList<Event> = mutableListOf()
) : BaseEntity()