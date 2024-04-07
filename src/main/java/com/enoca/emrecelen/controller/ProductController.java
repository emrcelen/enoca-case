package com.enoca.emrecelen.controller;

import com.enoca.emrecelen.controller.dto.request.product.CreateProductRequestDTO;
import com.enoca.emrecelen.controller.dto.request.product.UpdateProductRequestDTO;
import com.enoca.emrecelen.controller.mapper.RestResponseMapper;
import com.enoca.emrecelen.controller.mapper.product.ProductControllerMapper;
import com.enoca.emrecelen.controller.swagger.ProductControllerSwagger;
import com.enoca.emrecelen.model.enumaration.Constants;
import com.enoca.emrecelen.services.ProductService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping(path = "/product")
public class ProductController implements ProductControllerSwagger {
    private final ProductService productService;
    private final ProductControllerMapper productControllerMapper;

    public ProductController(ProductService productService, ProductControllerMapper productControllerMapper) {
        this.productService = productService;
        this.productControllerMapper = productControllerMapper;
    }

    @Override
    public ResponseEntity<?> findByProduct(UUID productId, String productName) {
        final var responseBO = this.productService.findByProduct(productId, productName);
        final var responseDTO = productControllerMapper.convertToResponseDTO(responseBO);
        final var response = RestResponseMapper.convertToSuccessResponse(responseDTO);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<?> createProduct(HttpHeaders headers, CreateProductRequestDTO request) {
        final var requestBO = productControllerMapper.convertCreateProductToBO(request);
        final var saveEntity = this.productService.createProduct(headers, requestBO);
        final var responseDTO = productControllerMapper.convertToResponseDTO(saveEntity);
        final var response = RestResponseMapper.convertToSuccessResponse(responseDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Override
    public ResponseEntity<?> updateProduct(HttpHeaders headers, UUID productId, UpdateProductRequestDTO request) {
        final var requestBO = productControllerMapper.convertUpdateProductToBO(request);
        final var updateEntity = this.productService.updateProduct(headers, productId, requestBO);
        final var responseDTO = productControllerMapper.convertToResponseDTO(updateEntity);
        final var response = RestResponseMapper.convertToSuccessResponse(responseDTO);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<?> deleteProduct(UUID productId) {
        final var deleteEntity = this.productService.deleteProduct(productId);
        final var responseDTO = productControllerMapper.convertToResponseDTO(deleteEntity);
        final var response = RestResponseMapper.convertToSuccessResponse(responseDTO);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<?> findAllOrderByStockQuantity(Constants.OrderBy order, int page, int size) {
        final var findAllEntity = this.productService.findAllOrderByStockQuantity(
                order,
                page,
                size
        );
        final var responseDTO = productControllerMapper.convertToResponseDTO(findAllEntity);
        final var response = RestResponseMapper.convertToSuccessResponse(responseDTO);
        return ResponseEntity.ok(response);
    }
}
