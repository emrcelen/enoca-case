package com.enoca.emrecelen.services;

import com.enoca.emrecelen.exception.IllegalParameterException;
import com.enoca.emrecelen.model.entity.Cart;
import com.enoca.emrecelen.model.entity.CartItem;
import com.enoca.emrecelen.model.entity.Product;
import com.enoca.emrecelen.repositories.CartItemRepository;
import com.enoca.emrecelen.services.mapper.CartItemsServiceMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;
import java.util.List;

@Service
public class CartItemService {
    private final CartItemRepository cartItemRepository;
    private final CartItemsServiceMapper cartItemsServiceMapper;

    public CartItemService(
            CartItemRepository cartItemRepository,
            CartItemsServiceMapper cartItemsServiceMapper
    ) {
        this.cartItemRepository = cartItemRepository;
        this.cartItemsServiceMapper = cartItemsServiceMapper;
    }

    protected boolean cartItemControl(List<UUID> cartItems) {
        if (cartItems != null && !cartItems.isEmpty()) {
            final var items = this.cartItemRepository.findAllById(cartItems);
            final var existingIds = items.stream()
                    .map(CartItem::getId).toList();
            final var anyNonExistingId = cartItems.stream()
                    .anyMatch(id -> !existingIds.contains(id));
            return anyNonExistingId;
        }
        throw new IllegalParameterException();
    }

    protected void deleteAll(List<CartItem> cartItems) {
        this.cartItemRepository.deleteAll(cartItems);
    }

    protected void deleteCartItem(CartItem cartItem) {
        this.cartItemRepository.delete(cartItem);
    }

    protected List<CartItem> findAllById(List<UUID> cartItems) {
        if (cartItems != null && !cartItems.isEmpty()) {
            final var items = this.cartItemRepository.findAllById(cartItems);
            return items;
        }
        throw new IllegalParameterException();
    }

    protected List<CartItem> updateCartItem(List<CartItem> cartItems) {
        cartItems.forEach(
                item -> item.setTotalItemPrice(
                        item.getProduct().getPrice().multiply
                                (BigDecimal.valueOf(item.getQuantity())))
        );
        final var response = this.cartItemRepository.saveAll(cartItems);
        return response;
    }

    protected CartItem updateCartItem(CartItem cartItem) {
        return this.cartItemRepository.save(cartItem);
    }

    protected CartItem productConvertCartItem(Cart cart, Product product, int quantity) {
        var entity = new CartItem.Builder()
                .cart(cart)
                .product(product)
                .quantity(quantity)
                .totalItemPrice(product.getPrice().multiply(BigDecimal.valueOf(quantity)))
                .build();
        entity = this.cartItemRepository.save(entity);
        return entity;
    }

    protected List<CartItem> saveCartItems(List<CartItem> items){
        return this.cartItemRepository.saveAll(items);
    }

}
