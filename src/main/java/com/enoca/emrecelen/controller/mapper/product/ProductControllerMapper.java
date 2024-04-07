package com.enoca.emrecelen.controller.mapper.product;

import com.enoca.emrecelen.controller.dto.request.product.CreateProductRequestDTO;
import com.enoca.emrecelen.controller.dto.request.product.UpdateProductRequestDTO;
import com.enoca.emrecelen.controller.dto.response.product.ProductResponseDTO;
import com.enoca.emrecelen.services.model.bo.ProductBO;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class ProductControllerMapper {
    public ProductBO convertCreateProductToBO(CreateProductRequestDTO request){
        return new ProductBO(
                null,
                request.name(),
                request.description(),
                request.price(),
                request.stockQuantity(),
                LocalDateTime.now(),
                null,
                null,
                null
        );
    }
    public ProductBO convertUpdateProductToBO(UpdateProductRequestDTO request){
        return new ProductBO(
                null,
                request.name().isPresent() ? request.name().get() : null,
                request.description().isPresent() ? request.description().get(): null,
                request.price().isPresent() ? request.price().get(): null,
                request.stockQuantity().isPresent() ? request.stockQuantity().get() : null,
                null,
                null,
                null,
                null
        );
    }

    public ProductResponseDTO convertToResponseDTO(ProductBO productBO){
        return new ProductResponseDTO(
                productBO.id(),
                productBO.name(),
                productBO.description(),
                productBO.price(),
                productBO.stockQuantity()
        );
    }

    public List<ProductResponseDTO> convertToResponseDTO(List<ProductBO> products) {
        return products.stream()
                .map(this::convertToResponseDTO)
                .toList();
    }
}
