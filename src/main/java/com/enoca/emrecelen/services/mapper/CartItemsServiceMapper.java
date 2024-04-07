package com.enoca.emrecelen.services.mapper;

import com.enoca.emrecelen.model.entity.CartItem;
import com.enoca.emrecelen.services.model.bo.CartItemBO;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class CartItemsServiceMapper {
    private final ProductServiceMapper productServiceMapper;

    public CartItemsServiceMapper(ProductServiceMapper productServiceMapper) {
        this.productServiceMapper = productServiceMapper;
    }

    public CartItemBO convertToBO(CartItem cartItem) {
        return new CartItemBO(
                cartItem.getId(),
                productServiceMapper.convertToBO(cartItem.getProduct()),
                cartItem.getCart().getId(),
                cartItem.getQuantity(),
                cartItem.getTotalItemPrice()
        );
    }

    public List<CartItemBO> convertToBO(List<CartItem> cartItems) {
        return cartItems.stream()
                .map(this::convertToBO)
                .collect(Collectors.toList());
    }

    public List<UUID> convertToUUID(List<CartItem> cartItems){
        return cartItems.stream()
                .map(CartItem::getId)
                .collect(Collectors.toList());
    }

}
