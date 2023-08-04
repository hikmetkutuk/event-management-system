package com.eventservice.model

import org.hibernate.annotations.GenericGenerator
import java.time.LocalDateTime
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.MappedSuperclass

@MappedSuperclass
open class BaseEntity @JvmOverloads constructor(
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    val id: String? = "",
    val active: Boolean = true,
    val isDeleted: Boolean = false,
    val insertedTime: LocalDateTime? = null,
    var updatedTime: LocalDateTime? = null
)