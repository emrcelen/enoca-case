package com.enoca.emrecelen.controller.dto.response.order;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
import java.util.List;

public record OrderResponseDTO(
        UUID id,
        UUID customer,
        List<UUID> orderItems,
        BigDecimal totalPrice,
        String orderDate
) {
}
