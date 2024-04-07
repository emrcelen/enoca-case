package com.enoca.emrecelen.services.model.bo;

import java.math.BigDecimal;
import java.util.UUID;
import java.util.List;

public record CartBO(
        UUID id,
        List<UUID> items,
        UUID customer,
        BigDecimal totalPrice
) {
}
