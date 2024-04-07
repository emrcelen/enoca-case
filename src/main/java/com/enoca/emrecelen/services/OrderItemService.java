package com.enoca.emrecelen.services;

import com.enoca.emrecelen.model.entity.CartItem;
import com.enoca.emrecelen.model.entity.Order;
import com.enoca.emrecelen.model.entity.OrderItem;
import com.enoca.emrecelen.repositories.OrderItemRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderItemService {
    private final OrderItemRepository orderItemRepository;

    public OrderItemService(OrderItemRepository orderItemRepository) {
        this.orderItemRepository = orderItemRepository;
    }

    protected List<OrderItem> createOrderItems(List<CartItem> cartItems, Order order){
        var orderItems = cartItems.stream()
                .map(item -> convertOrderItem(item, order))
                .toList();

        orderItems = this.orderItemRepository.saveAll(orderItems);
        return orderItems;
    }

    private OrderItem convertOrderItem(CartItem cartItem, Order order){
        return new OrderItem.Builder()
                .order(order)
                .product(cartItem.getProduct())
                .quantity(cartItem.getQuantity())
                .unitPrice(cartItem.getProduct().getPrice())
                .build();
    }
}
