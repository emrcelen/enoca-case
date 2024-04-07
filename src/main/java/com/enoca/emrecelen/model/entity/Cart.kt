package com.enoca.emrecelen.model.entity

import jakarta.persistence.*
import org.hibernate.annotations.GenericGenerator
import java.math.BigDecimal
import java.util.UUID

@Entity
@Table(name = "carts")
data class Cart(
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    val id: UUID? = null,
    @OneToMany(mappedBy = "cart", cascade = arrayOf(CascadeType.ALL))
    var items: List<CartItem> = mutableListOf(),
    @OneToOne
    @JoinColumn(name = "customer_id", unique = true)
    val customer: Customer? = null,
    var totalPrice: BigDecimal
) {
    constructor() : this(Builder())

    constructor(builder: Builder) : this(
        id = builder.id,
        items = builder.items,
        customer = builder.customer,
        totalPrice = builder.totalPrice
    )

    class Builder {
        var id: UUID? = null
            private set
        var items: List<CartItem> = mutableListOf()
            private set
        var customer: Customer = Customer()
            private set
        var totalPrice: BigDecimal = BigDecimal.ZERO
            private set

        fun id(id: UUID) = apply { this.id = id }
        fun items(items: List<CartItem>) = apply { this.items = items }
        fun customer(customer: Customer) = apply { this.customer = customer }
        fun totalPrice(totalPrice: BigDecimal) = apply { this.totalPrice = totalPrice }
        fun build() = Cart(this)
    }

    override fun hashCode(): Int {
        return id?.hashCode() ?: 0
    }

}