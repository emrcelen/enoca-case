package com.enoca.emrecelen.services.model.bo;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
import java.util.List;

public record OrderBO(
        UUID id,
        UUID customer,
        List<UUID> orderItems,
        BigDecimal totalPrice,
        LocalDateTime createdOn,
        LocalDateTime modifiedOn,
        UUID createdById,
        UUID modifiedById
) {
}
