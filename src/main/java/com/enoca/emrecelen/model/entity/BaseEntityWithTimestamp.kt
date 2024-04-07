package com.enoca.emrecelen.model.entity

import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.MappedSuperclass
import org.hibernate.annotations.GenericGenerator
import java.time.LocalDateTime
import java.util.UUID

@MappedSuperclass
open class BaseEntityWithTimestamp {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    open var id: UUID? = null
    open var createdOn: LocalDateTime = LocalDateTime.now()
    open var modifiedOn: LocalDateTime? = null

    constructor() {
        this.id = null
        this.createdOn = LocalDateTime.now()
        this.modifiedOn = null
    }
    constructor(
        id: UUID,
        createdOn: LocalDateTime,
        modifiedOn: LocalDateTime
    ) {
        this.id = id
        this.createdOn = createdOn
        this.modifiedOn = modifiedOn
    }
}
