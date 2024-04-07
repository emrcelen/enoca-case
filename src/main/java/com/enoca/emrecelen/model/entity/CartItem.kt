package com.enoca.emrecelen.model.entity

import jakarta.persistence.*
import org.hibernate.annotations.GenericGenerator
import java.math.BigDecimal
import java.util.*

@Entity
@Table(name = "cart_items")
data class CartItem(
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    val id: UUID? = null,
    @ManyToOne
    @JoinColumn(name = "product_id")
    val product: Product,
    @ManyToOne
    @JoinColumn(name = "cart_id")
    val cart: Cart,
    var quantity: Int,
    var totalItemPrice: BigDecimal
) {
    constructor() : this(Builder())

    constructor(builder: Builder) : this(
        id = builder.id,
        product = builder. product,
        cart = builder.cart,
        quantity = builder.quantity,
        totalItemPrice = builder.totalItemPrice
    )

    class Builder {
        var id: UUID? = null
            private set
        var product: Product = Product()
            private set
        var cart: Cart = Cart()
            private set
        var quantity: Int = 0
            private set
        var totalItemPrice: BigDecimal = BigDecimal.ZERO
            private set

        fun id(id: UUID) = apply { this.id = id }
        fun product(product: Product) = apply { this.product = product }
        fun cart(cart: Cart) = apply { this.cart = cart }
        fun quantity(quantity: Int) = apply { this.quantity = quantity }
        fun totalItemPrice(totalItemPrice: BigDecimal) = apply { this.totalItemPrice = totalItemPrice }
        fun build() = CartItem(this)
    }
}