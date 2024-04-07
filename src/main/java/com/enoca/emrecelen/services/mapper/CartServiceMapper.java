package com.enoca.emrecelen.services.mapper;

import com.enoca.emrecelen.model.entity.Cart;
import com.enoca.emrecelen.services.model.bo.CartBO;
import org.springframework.stereotype.Component;

@Component
public class CartServiceMapper {

    private final CartItemsServiceMapper cartItemsServiceMapper;

    public CartServiceMapper(CartItemsServiceMapper cartItemsServiceMapper) {
        this.cartItemsServiceMapper = cartItemsServiceMapper;
    }

    public CartBO convertToBO(Cart cart) {
        return new CartBO(
                cart.getId(),
                cartItemsServiceMapper.convertToUUID(cart.getItems()),
                cart.getCustomer() != null ? cart.getCustomer().getId() : null,
                cart.getTotalPrice()
        );
    }
}
