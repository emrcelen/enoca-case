package com.enoca.emrecelen.controller.dto.response.customer;

import io.swagger.v3.oas.annotations.media.Schema;

public record LoginCustomerResponseDTO(
        @Schema(example = "me@emrecelen.com.tr")
        String email,
        @Schema(example = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJtZUBlbXJlY2VsZW4uY29tLnRyIiwicm9sZSI6IlVTRVIiLCJpYXQiOjE3MTI0OTM3NTMsImV4cCI6MTcxMjUwODE1M30.1v0wbjM4Mk7dUCGHRElIPKcU8NvTVIZcuFiDQW3MHZEWa9akotZ3mcAOZw7j6NPK")
        String token
) {
}
