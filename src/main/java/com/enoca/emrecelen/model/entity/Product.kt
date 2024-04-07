package com.enoca.emrecelen.model.entity

import jakarta.persistence.Entity
import jakarta.persistence.Table
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.UUID

@Entity
@Table(name = "products")
data class Product(
    var name: String,
    var description: String,
    var price: BigDecimal,
    var stockQuantity: Int
) : BaseEntityWithOwnerTimestamp() {

    constructor() : this(Builder())

    constructor(builder: Builder) : this(
        name = builder.name,
        description = builder.description,
        price = builder.price,
        stockQuantity = builder.stockQuantity
    ) {
        super.id = builder.id
        super.createdOn = builder.createdOn
        super.modifiedOn = builder.modifiedOn
        super.createdById = builder.createdById
        super.modifiedById = builder.modifiedById
    }

    class Builder {
        var id: UUID? = null
            private set
        var createdOn: LocalDateTime = LocalDateTime.now()
            private set
        var modifiedOn: LocalDateTime? = null
            private set
        var createdById: UUID? = null
            private set
        var modifiedById: UUID? = null
            private set
        var name: String = ""
            private set
        var description: String = ""
            private set
        var price: BigDecimal = BigDecimal.ZERO
            private set
        var stockQuantity: Int = 0
            private set

        fun id(id: UUID) = apply { this.id = id }
        fun createdOn(createdOn: LocalDateTime) = apply { this.createdOn = createdOn }
        fun modifiedOn(modifiedOn: LocalDateTime) = apply { this.modifiedOn = modifiedOn }
        fun createdById(createdById: UUID) = apply { this.createdById = createdById }
        fun modifiedById(modifiedById: UUID) = apply { this.modifiedById = modifiedById }
        fun name(name: String) = apply { this.name = name}
        fun description(description: String) = apply { this.description = description }
        fun price(price: BigDecimal) = apply { this.price = price }
        fun stockQuantity(stockQuantity: Int) = apply { this.stockQuantity = stockQuantity }
        fun build() = Product(this)
    }
}