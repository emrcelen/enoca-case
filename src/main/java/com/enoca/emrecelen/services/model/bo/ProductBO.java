package com.enoca.emrecelen.services.model.bo;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record ProductBO(
        UUID id,
        String name,
        String description,
        BigDecimal price,
        Integer stockQuantity,
        LocalDateTime createdOn,
        LocalDateTime modifiedOn,
        UUID createdById,
        UUID modifiedById
) {
}
