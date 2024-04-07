package com.enoca.emrecelen.services.mapper;

import com.enoca.emrecelen.model.entity.Product;
import com.enoca.emrecelen.services.model.bo.ProductBO;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProductServiceMapper {

    public ProductBO convertToBO(Product product){
        return new ProductBO(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getStockQuantity(),
                product.getCreatedOn(),
                product.getModifiedOn(),
                product.getCreatedById(),
                product.getModifiedById()
        );
    }

    public List<ProductBO> convertToBO(List<Product> products){
        return products.stream()
                .map(this::convertToBO)
                .collect(Collectors.toList());
    }
}
