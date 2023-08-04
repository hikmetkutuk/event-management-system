package com.eventservice.model

import javax.persistence.Entity
import javax.persistence.OneToMany
import javax.persistence.Table

@Entity
@Table(name = "organizers")
data class Organizer(
    val name: String,
    val contactInformation: String,
    @OneToMany(mappedBy = "organizer")
    var events: MutableList<Event> = mutableListOf()
) : BaseEntity()