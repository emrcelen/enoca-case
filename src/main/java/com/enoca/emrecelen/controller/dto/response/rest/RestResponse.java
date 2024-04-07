package com.enoca.emrecelen.controller.dto.response.rest;

import io.swagger.v3.oas.annotations.media.Schema;

public record RestResponse<T>(
        T payload,
        @Schema(example = "true")
        boolean isSuccess,
        @Schema(example = "07/04/2024 15:17:55")
        String responseDate
) {
}
