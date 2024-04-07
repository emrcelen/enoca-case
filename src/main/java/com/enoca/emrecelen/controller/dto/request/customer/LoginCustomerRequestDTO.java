package com.enoca.emrecelen.controller.dto.request.customer;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record LoginCustomerRequestDTO(
        @Schema(example = "me@emrecelen.com.tr")
        @Email(regexp = "^(?=.{1,256}$)[a-zA-Z0-9_-]+(?:\\.[a-zA-Z0-9_-]+)*@[a-zA-Z0-9_-]+(?:\\.[a-zA-Z0-9_-]+)+$", message = "Please enter a valid email address.")
        @NotEmpty(message = "You cannot leave this email field empty.")
        @NotNull(message = "You cannot leave this email field null.")
        @NotBlank(message = "You cannot leave this email field blank.")
        String email,
        @Schema(example = "me")
        @NotEmpty(message = "You cannot leave this password field empty.")
        @NotNull(message = "You cannot leave this password field null.")
        @NotBlank(message = "You cannot leave this password field blank.")
        String password
) {
}
