package com.enoca.emrecelen.services.model.bo;

import java.math.BigDecimal;
import java.util.UUID;

public record CartItemBO(
        UUID id,
        ProductBO product,
        UUID cart,
        int quantity,
        BigDecimal totalItemPrice
) {
}
