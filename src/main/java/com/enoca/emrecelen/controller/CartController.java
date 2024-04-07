package com.enoca.emrecelen.controller;

import com.enoca.emrecelen.controller.mapper.RestResponseMapper;
import com.enoca.emrecelen.controller.mapper.cart.CartControllerMapper;
import com.enoca.emrecelen.controller.swagger.CartControllerSwagger;
import com.enoca.emrecelen.services.CartService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping(path = "/cart")
public class CartController implements CartControllerSwagger {

    private final CartService cartService;
    private final CartControllerMapper cartControllerMapper;

    public CartController(CartService cartService, CartControllerMapper cartControllerMapper) {
        this.cartService = cartService;
        this.cartControllerMapper = cartControllerMapper;
    }


    @Override
    public ResponseEntity<?> findByCart(HttpHeaders headers) {
        final var responseBO = this.cartService.findByCart(headers);
        final var responseDTO = cartControllerMapper.convertToResponseDTO(responseBO);
        final var response = RestResponseMapper.convertToSuccessResponse(responseDTO);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<?> emptyCart(HttpHeaders headers) {
        final var responseBO = this.cartService.emptyCart(headers);
        final var responseDTO = cartControllerMapper.convertToResponseDTO(responseBO);
        final var response = RestResponseMapper.convertToSuccessResponse(responseDTO);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<?> addProductToCart(HttpHeaders headers, UUID productId, Integer quantity) {
        final var responseBO = this.cartService.addProductToCart(
                headers,
                productId,
                quantity
        );
        final var responseDTO = cartControllerMapper.convertToResponseDTO(responseBO);
        final var response = RestResponseMapper.convertToSuccessResponse(responseDTO);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<?> removeProductFromCart(HttpHeaders headers, UUID productId, Integer quantity) {
        final var responseBO = this.cartService.removeProductFromCart(
                headers,
                productId,
                quantity
        );
        final var responseDTO = cartControllerMapper.convertToResponseDTO(responseBO);
        final var response = RestResponseMapper.convertToSuccessResponse(responseDTO);
        return ResponseEntity.ok(response);
    }
}
