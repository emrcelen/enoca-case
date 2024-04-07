package com.enoca.emrecelen.controller.swagger;

import com.enoca.emrecelen.controller.dto.request.product.CreateProductRequestDTO;
import com.enoca.emrecelen.controller.dto.request.product.UpdateProductRequestDTO;
import com.enoca.emrecelen.controller.dto.response.rest.RestResponse;
import com.enoca.emrecelen.model.enumaration.Constants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

@Tag(
        name = "Product Controller",
        description = "Endpoints for managing product information, including creation, retrieval, update, and deletion."
)
@SecurityScheme(
        name = "Bearer Authentication",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        scheme = "bearer"
)
public interface ProductControllerSwagger {

    @GetMapping
    @Operation(
            summary = "You can find product with this endpoint.",
            parameters = {
                    @Parameter(
                            name = "id",
                            required = false,
                            example = "d5de4f48-d34c-4aae-80dc-5afafe9cba78"
                    ),
                    @Parameter(
                            name = "name",
                            required = false,
                            example = "Apple"
                    )
            },
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
    ResponseEntity<?> findByProduct(
            @RequestParam(required = false, name = "id") UUID productId,
            @RequestParam(required = false, name = "name") String productName
    );

    @PostMapping
    @Operation(
            summary = "You can create product with this endpoint.",
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
    @SecurityRequirement(name = "Bearer Authentication")
    ResponseEntity<?> createProduct(
            @RequestHeader HttpHeaders headers,
            @RequestBody @Valid CreateProductRequestDTO request
    );

    @PutMapping(path = "/{id}")
    @Operation(
            summary = "You can update product with this endpoint.",
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
    ResponseEntity<?> updateProduct(
            @RequestHeader HttpHeaders headers,
            @PathVariable(name = "id") UUID productId,
            @RequestBody @Valid UpdateProductRequestDTO request
    );

    @DeleteMapping(path = "/{id}")
    @Operation(
            summary = "You can delete product with this endpoint.",
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
    ResponseEntity<?> deleteProduct(
            @PathVariable(name = "id") UUID productId
    );

    @GetMapping(path = "/findAll/{order}/{page}/{size}")
    @Operation(
            summary = "Get all products ordered by stock qunatity.",
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
    @SecurityRequirement(name = "Bearer Authentication")
    ResponseEntity<?> findAllOrderByStockQuantity(
            @PathVariable(name = "order")Constants.OrderBy order,
            @PathVariable(name = "page") @Valid @Positive int page,
            @PathVariable(name = "size") @Valid @Positive int size
    );

}
