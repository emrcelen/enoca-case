package com.enoca.emrecelen.controller.dto.response.cart;

import java.math.BigDecimal;
import java.util.UUID;
import java.util.List;

public record CartResponseDTO(
        UUID id,
        UUID customer,
        List<UUID> items,
        BigDecimal totalPrice
) {
}
