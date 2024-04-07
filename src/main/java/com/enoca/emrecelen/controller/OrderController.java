package com.enoca.emrecelen.controller;

import com.enoca.emrecelen.controller.mapper.RestResponseMapper;
import com.enoca.emrecelen.controller.mapper.order.OrderControllerMapper;
import com.enoca.emrecelen.controller.swagger.OrderControllerSwagger;
import com.enoca.emrecelen.services.OrderService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping(path = "/order")
public class OrderController implements OrderControllerSwagger {
    private final OrderService orderService;
    private final OrderControllerMapper orderControllerMapper;

    public OrderController(OrderService orderService, OrderControllerMapper orderControllerMapper) {
        this.orderService = orderService;
        this.orderControllerMapper = orderControllerMapper;
    }

    @Override
    public ResponseEntity<?> placeOrder(HttpHeaders headers) {
        final var saveEntity = this.orderService.placeOrder(headers);
        final var responseDTO = orderControllerMapper.convertToResponseDTO(saveEntity);
        final var response = RestResponseMapper.convertToSuccessResponse(responseDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Override
    public ResponseEntity<?> getOrderForCode(HttpHeaders headers, UUID orderId) {
        final var responseBO = this.orderService.getOrderForCode(headers, orderId);
        final var responseDTO = orderControllerMapper.convertToResponseDTO(responseBO);
        final var response = RestResponseMapper.convertToSuccessResponse(responseDTO);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<?> getAllOrdersForCustomer(HttpHeaders headers) {
        final var responseBO = this.orderService.getAllOrdersForCustomer(headers);
        final var responseDTO = orderControllerMapper.convertToResponseDTO(responseBO);
        final var response = RestResponseMapper.convertToSuccessResponse(responseDTO);
        return ResponseEntity.ok(response);
    }
}
