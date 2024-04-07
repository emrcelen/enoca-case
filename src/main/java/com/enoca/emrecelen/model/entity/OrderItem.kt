package com.enoca.emrecelen.model.entity

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import org.hibernate.annotations.GenericGenerator
import java.math.BigDecimal
import java.util.UUID

@Entity
@Table(name = "order_items")
data class OrderItem(
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    val id: UUID? = null,
    @ManyToOne
    @JoinColumn(name = "order_id")
    val order: Order,
    @ManyToOne
    @JoinColumn(name = "product_id")
    val product: Product,
    val quantity: Int,
    val unitPrice: BigDecimal
) {
    constructor() : this (Builder())

    constructor(builder: Builder) : this(
        id = builder.id,
        order = builder.order,
        product = builder.product,
        quantity = builder.quantity,
        unitPrice = builder.unitPrice
    )

    class Builder {
        var id: UUID? = null
            private set
        var order: Order = Order()
            private set
        var product: Product = Product()
            private set
        var quantity: Int = 0
            private set
        var unitPrice: BigDecimal = BigDecimal.ZERO
            private set

        fun id(id: UUID) = apply { this.id = id }
        fun order(order: Order) = apply { this.order = order }
        fun product(product: Product) = apply { this.product = product }
        fun quantity(quantity: Int) = apply { this.quantity = quantity }
        fun unitPrice(unitPrice: BigDecimal) = apply { this.unitPrice = unitPrice }
        fun build() = OrderItem(this)
    }
}