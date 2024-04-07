package com.enoca.emrecelen.model.entity

import jakarta.persistence.*
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.UUID

@Entity
@Table(name = "orders")
data class Order(
    @ManyToOne
    @JoinColumn(name = "customer_id")
    val customer: Customer,
    @OneToMany(mappedBy = "order", cascade = arrayOf(CascadeType.ALL))
    var orderItems: List<OrderItem> = mutableListOf(),
    var totalPrice: BigDecimal
) : BaseEntityWithOwnerTimestamp() {

    constructor() : this(Builder())

    constructor(builder: Builder) : this(
        customer = builder.customer,
        orderItems = builder.orderItems,
        totalPrice = builder.totalPrice
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
        var customer: Customer = Customer()
            private set
        var orderItems: List<OrderItem> = mutableListOf()
            private set
        var totalPrice: BigDecimal = BigDecimal.ZERO
            private set

        fun id(id: UUID) = apply { this.id = id }
        fun createdOn(createdOn: LocalDateTime) = apply { this.createdOn = createdOn }
        fun modifiedOn(modifiedOn: LocalDateTime) = apply { this.modifiedOn = modifiedOn }
        fun createdById(createdById: UUID) = apply { this.createdById = createdById }
        fun modifiedById(modifiedById: UUID) = apply { this.modifiedById = modifiedById }
        fun customer(customer: Customer) = apply { this.customer = customer }
        fun orderItems(orderItems: List<OrderItem>) = apply { this.orderItems = orderItems }
        fun totalPrice(totalPrice: BigDecimal) = apply { this.totalPrice = totalPrice }
        fun build() = Order(this)
    }
}