package com.enoca.emrecelen.services.mapper;

import com.enoca.emrecelen.model.entity.Order;
import com.enoca.emrecelen.services.model.bo.OrderBO;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class OrderServiceMapper {
    private final OrderItemServiceMapper orderItemServiceMapper;

    public OrderServiceMapper(OrderItemServiceMapper orderItemServiceMapper) {
        this.orderItemServiceMapper = orderItemServiceMapper;
    }

    public OrderBO convertToBO(Order order){
        return new OrderBO(
                order.getId(),
                order.getCustomer().getId(),
                orderItemServiceMapper.convertToUUID(order.getOrderItems()),
                order.getTotalPrice(),
                order.getCreatedOn(),
                order.getModifiedOn(),
                order.getCreatedById(),
                order.getModifiedById()
        );
    }

    public List<OrderBO> convertToBO(List<Order> orders){
        return orders.stream()
                .map(this::convertToBO)
                .collect(Collectors.toList());
    }

    public List<UUID> convertToUUID(List<Order> orders){
        return orders.stream()
                .map(Order::getId)
                .collect(Collectors.toList());
    }

}
