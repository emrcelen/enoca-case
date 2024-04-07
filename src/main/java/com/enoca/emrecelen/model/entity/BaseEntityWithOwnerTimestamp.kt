package com.enoca.emrecelen.model.entity

import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.MappedSuperclass
import org.hibernate.annotations.GenericGenerator
import java.time.LocalDateTime
import java.util.UUID

@MappedSuperclass
open class BaseEntityWithOwnerTimestamp {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    open var id: UUID? = null
    open var createdOn: LocalDateTime = LocalDateTime.now()
    open var modifiedOn: LocalDateTime? = null
    open var createdById: UUID? = null
    open var modifiedById: UUID? = null

    constructor() {
        this.id = null
        this.createdOn = LocalDateTime.now()
        this.modifiedOn = null
        this.createdById = null
        this.modifiedById = null
    }
    constructor(
        id: UUID,
        createdOn: LocalDateTime,
        modifiedOn: LocalDateTime,
        createdById: UUID,
        modifiedById: UUID
    ) {
        this.id = id
        this.createdOn = createdOn
        this.modifiedOn = modifiedOn
        this.createdById = createdById
        this.modifiedById = modifiedById
    }
}