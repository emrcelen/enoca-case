package com.enoca.emrecelen.controller.dto.response.customer;

import io.swagger.v3.oas.annotations.media.Schema;

public record RegisterCustomerResponseDTO(
        @Schema(example = "me@emrecelen.com.tr")
        String email,
        @Schema(example = "ADMIN")
        String role
) {
}
