package com.enoca.emrecelen.services.model.bo;

import java.math.BigDecimal;
import java.util.UUID;

public record OrderItemBO(
        UUID id,
        UUID order,
        ProductBO product,
        int quantity,
        BigDecimal unitPrice
) {
}
