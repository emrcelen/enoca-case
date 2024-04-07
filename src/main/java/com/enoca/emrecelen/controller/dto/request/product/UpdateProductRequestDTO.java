package com.enoca.emrecelen.controller.dto.request.product;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.util.Optional;

public record UpdateProductRequestDTO(
        @Schema(example = "Apple")
        Optional<String> name,
        @Schema(example = "This is a description for apple")
        Optional<String> description,
        Optional<BigDecimal> price,
        Optional<Integer> stockQuantity
) {
}
