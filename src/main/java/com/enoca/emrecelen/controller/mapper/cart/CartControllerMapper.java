package com.enoca.emrecelen.controller.mapper.cart;

import com.enoca.emrecelen.controller.dto.response.cart.CartResponseDTO;
import com.enoca.emrecelen.services.model.bo.CartBO;
import org.springframework.stereotype.Component;

@Component
public class CartControllerMapper {

    public CartResponseDTO convertToResponseDTO(CartBO cart){
        return new CartResponseDTO(
                cart.id(),
                cart.customer(),
                cart.items(),
                cart.totalPrice()
        );
    }
}
