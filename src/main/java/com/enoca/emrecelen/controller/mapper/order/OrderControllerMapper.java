package com.enoca.emrecelen.controller.mapper.order;

import com.enoca.emrecelen.controller.dto.response.order.OrderResponseDTO;
import com.enoca.emrecelen.services.model.bo.OrderBO;
import org.springframework.stereotype.Component;

import java.util.List;
import java.time.format.DateTimeFormatter;

@Component
public class OrderControllerMapper {
    private final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    public OrderResponseDTO convertToResponseDTO(OrderBO orderBO) {
        return new OrderResponseDTO(
                orderBO.id(),
                orderBO.customer(),
                orderBO.orderItems(),
                orderBO.totalPrice(),
                dtf.format(orderBO.createdOn())
        );
    }

    public List<OrderResponseDTO> convertToResponseDTO(List<OrderBO> orders) {
        return orders.stream()
                .map(this::convertToResponseDTO)
                .toList();
    }
}
