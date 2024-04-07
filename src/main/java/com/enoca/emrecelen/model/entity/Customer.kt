package com.enoca.emrecelen.model.entity

import com.enoca.emrecelen.model.enumaration.Constants.Role
import jakarta.persistence.*
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.time.LocalDateTime
import java.util.UUID

@Entity
@Table(name = "customers")
data class Customer(
    val email: String,
    @get:JvmName("getPass")
    var password: String,
    @Enumerated(EnumType.STRING)
    val role: Role,
    @OneToOne(mappedBy = "customer", cascade = arrayOf(CascadeType.ALL))
    var cart: Cart?,
    @OneToMany(mappedBy = "customer")
    val orders: List<Order> = mutableListOf()
) : BaseEntityWithTimestamp(), UserDetails {
    constructor() : this(Builder())

    constructor(builder: Builder) : this(
        email = builder.email,
        password = builder.password,
        role = builder.role,
        cart = builder.cart,
        orders = builder.orders
    ) {
        super.id = builder.id
        super.createdOn = builder.createdOn
        super.modifiedOn = builder.modifiedOn
    }

    class Builder {
        var id: UUID? = null
            private set
        var email: String = ""
            private set
        var password: String = ""
            private set
        var role: Role = Role.USER
            private set
        var cart: Cart? = null
            private set
        var orders: List<Order> = mutableListOf()
        var createdOn: LocalDateTime = LocalDateTime.now()
            private set
        var modifiedOn: LocalDateTime? = null
            private set

        fun id(id: UUID) = apply { this.id = id }
        fun email(email: String) = apply { this.email = email }
        fun password(password: String) = apply { this.password = password }
        fun role(role: Role) = apply { this.role = role }
        fun cart(cart: Cart) = apply { this.cart = cart }
        fun orders(orders: List<Order>) = apply { this.orders = orders }
        fun createdOn(createdOn: LocalDateTime) = apply { this.createdOn = createdOn }
        fun modifiedOn(modifiedOn: LocalDateTime) = apply { this.modifiedOn = modifiedOn }
        fun build() = Customer(this)
    }

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        return mutableListOf<GrantedAuthority>(
            SimpleGrantedAuthority(role.name)
        )
    }

    override fun getPassword(): String {
        return password
    }

    override fun getUsername(): String {
        return email
    }

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun isEnabled(): Boolean {
        return true
    }
}