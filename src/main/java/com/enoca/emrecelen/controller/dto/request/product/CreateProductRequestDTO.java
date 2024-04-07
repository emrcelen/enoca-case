package com.enoca.emrecelen.controller.dto.request.product;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;

public record CreateProductRequestDTO(
        @Schema(example = "Apple")
        @NotEmpty(message = "You cannot leave this name field empty.")
        @NotNull(message = "You cannot leave this name field null.")
        @NotBlank(message = "You cannot leave this name field blank.")
        String name,
        @Schema(example = "This is a description for apple")
        @NotEmpty(message = "You cannot leave this description field empty.")
        @NotNull(message = "You cannot leave this description field null.")
        @NotBlank(message = "You cannot leave this description field blank.")
        String description,
        @PositiveOrZero(message = "You cannot leave this price field negative")
        BigDecimal price,
        @PositiveOrZero(message = "You cannot leave this stock quantity field negative")
        int stockQuantity
) {
}
