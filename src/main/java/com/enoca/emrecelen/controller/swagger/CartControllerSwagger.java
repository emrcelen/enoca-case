package com.enoca.emrecelen.controller.swagger;

import com.enoca.emrecelen.controller.dto.response.rest.RestResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

@Tag(
        name = "Cart Controller",
        description = "Operations related to managing a shopping cart, including adding, updating, and removing products."
)
@SecurityScheme(
        name = "Bearer Authentication",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        scheme = "bearer"
)
public interface CartControllerSwagger {

    @GetMapping
    @Operation(
            summary = "You can find cart with this endpoint.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            array = @ArraySchema(schema = @Schema(implementation = RestResponse.class))
                                    )
                            }
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            array = @ArraySchema(schema = @Schema(implementation = RestResponse.class))
                                    )
                            }
                    )
            }
    )
    @SecurityRequirement(name = "Bearer Authentication")
    ResponseEntity<?> findByCart(@RequestHeader HttpHeaders headers);



    @GetMapping(path = "/clear")
    @Operation(
            summary = "Clear the contents of the shopping cart.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            array = @ArraySchema(schema = @Schema(implementation = RestResponse.class))
                                    )
                            }
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            array = @ArraySchema(schema = @Schema(implementation = RestResponse.class))
                                    )
                            }
                    )
            }
    )
    @SecurityRequirement(name = "Bearer Authentication")
    ResponseEntity<?> emptyCart(@RequestHeader HttpHeaders headers);

    @PostMapping(path = "/add/product/{id}")
    @Operation(
            summary = "Add a product to the shopping cart.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            array = @ArraySchema(schema = @Schema(implementation = RestResponse.class))
                                    )
                            }
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            array = @ArraySchema(schema = @Schema(implementation = RestResponse.class))
                                    )
                            }
                    )
            }
    )
    @SecurityRequirement(name = "Bearer Authentication")
    ResponseEntity<?> addProductToCart(
            @RequestHeader HttpHeaders headers,
            @PathVariable(name = "id") UUID productId,
            @Valid @RequestParam(name = "quantity", required = true) @NotNull @Positive Integer quantity
    );

    @PostMapping(path = "/remove/product/{id}")
    @Operation(
            summary = "Remove a product to the shopping cart.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            array = @ArraySchema(schema = @Schema(implementation = RestResponse.class))
                                    )
                            }
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            array = @ArraySchema(schema = @Schema(implementation = RestResponse.class))
                                    )
                            }
                    )
            }
    )
    @SecurityRequirement(name = "Bearer Authentication")
    ResponseEntity<?> removeProductFromCart(
            @RequestHeader HttpHeaders headers,
            @PathVariable(name = "id") UUID productId,
            @Valid @RequestParam(name = "quantity", required = true) @NotNull @Positive Integer quantity
    );
}
