package com.enoca.emrecelen.controller.swagger;

import com.enoca.emrecelen.controller.dto.request.customer.LoginCustomerRequestDTO;
import com.enoca.emrecelen.controller.dto.request.customer.RegisterCustomerRequestDTO;
import com.enoca.emrecelen.controller.dto.response.rest.RestResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(
        name = "Customer Controller",
        description = "A controller containing endpoint for customers to create an account and login themeselves."
)
@SecurityScheme(
        name = "Bearer Authentication",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        scheme = "bearer"
)
public interface CustomerControllerSwagger {

    @Operation(
            summary = "You can register with this endpoint.",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            array = @ArraySchema(schema = @Schema(implementation = RestResponse.class))
                                    )
                            }
                    )
            }
    )
    @PostMapping(path = "/register")
    ResponseEntity<?> register(@Valid @RequestBody RegisterCustomerRequestDTO request);

    @Operation(summary = "You can login with this endpoint.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            array = @ArraySchema(schema = @Schema(implementation = RestResponse.class))
                                    )
                            }
                    )
            }
    )
    @PostMapping(path = "/login")
    ResponseEntity<?> login(@Valid @RequestBody LoginCustomerRequestDTO request);
}
