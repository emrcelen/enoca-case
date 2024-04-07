package com.enoca.emrecelen.controller.dto.response.product;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.util.UUID;

public record ProductResponseDTO(
        UUID id,
        @Schema(example = "Apple")
        String name,
        @Schema(example = "This is a description for apple")
        String description,
        BigDecimal price,
        int stockQuantity
) {
}
