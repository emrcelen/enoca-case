package com.enoca.emrecelen.services.mapper;

import com.enoca.emrecelen.model.entity.OrderItem;
import com.enoca.emrecelen.services.model.bo.OrderItemBO;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class OrderItemServiceMapper {

    private final ProductServiceMapper productServiceMapper;

    public OrderItemServiceMapper(ProductServiceMapper productServiceMapper) {
        this.productServiceMapper = productServiceMapper;
    }

    public OrderItemBO convertToBO(OrderItem orderItem) {
        return new OrderItemBO(
                orderItem.getId(),
                orderItem.getOrder().getId(),
                productServiceMapper.convertToBO(orderItem.getProduct()),
                orderItem.getQuantity(),
                orderItem.getUnitPrice()
        );
    }

    public List<OrderItemBO> convertToBO(List<OrderItem> orderItems) {
        return orderItems.stream()
                .map(this::convertToBO)
                .collect(Collectors.toList());
    }

    public List<UUID> convertToUUID(List<OrderItem> orderItems){
        return orderItems.stream()
                .map(OrderItem::getId)
                .collect(Collectors.toList());
    }

}
