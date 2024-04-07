package com.enoca.emrecelen.services;

import com.enoca.emrecelen.exception.IllegalParameterException;
import com.enoca.emrecelen.exception.IllegalRequestBodyException;
import com.enoca.emrecelen.exception.ProductNotFoundException;
import com.enoca.emrecelen.model.entity.Cart;
import com.enoca.emrecelen.model.entity.CartItem;
import com.enoca.emrecelen.model.entity.Product;
import com.enoca.emrecelen.model.enumaration.Constants;
import com.enoca.emrecelen.repositories.ProductRepository;
import com.enoca.emrecelen.services.mapper.ProductServiceMapper;
import com.enoca.emrecelen.services.model.bo.ProductBO;
import com.enoca.emrecelen.utilities.CustomerUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
import java.util.List;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductServiceMapper productServiceMapper;
    private final CustomerUtils customerUtils;
    private final Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

    public ProductService(
            ProductRepository productRepository,
            ProductServiceMapper productServiceMapper,
            CustomerUtils customerUtils
    ) {
        this.productRepository = productRepository;
        this.productServiceMapper = productServiceMapper;
        this.customerUtils = customerUtils;
    }

    public ProductBO findByProduct(UUID productId, String productName) {
        if (productId != null && !productId.toString().isEmpty() && productName == null)
            return findByProductId(productId);
        else if (productName != null && !productName.isEmpty() && productId == null)
            return findByProductName(productName);
        throw new IllegalParameterException();
    }

    protected Product findByProductIdForCartItem(UUID productId) {
        if (productId != null && !productId.toString().isEmpty()) {
            logger.info("Request product id: {}", productId);
            final var entity = this.productRepository.findById(productId)
                    .orElseThrow(
                            () -> new ProductNotFoundException(null, productId)
                    );
            logger.info("Response find by product id: {}", entity);
            return entity;
        }
        throw new IllegalParameterException();
    }

    @Transactional
    public ProductBO createProduct(HttpHeaders headers, ProductBO productBO) {
        if (validateProductData(productBO) && !this.productRepository.existsByName(productBO.name())) {
            var convert = new Product.Builder()
                    .name(productBO.name())
                    .description(productBO.description())
                    .price(productBO.price())
                    .stockQuantity(productBO.stockQuantity())
                    .createdOn(LocalDateTime.now())
                    .createdById(customerUtils.findByCustomerId(headers))
                    .build();
            logger.info("Create Product Request Data: {}", convert);
            final var entity = this.productRepository.save(convert);
            logger.info("Create Product Entity: {}", entity);
            final var response = productServiceMapper.convertToBO(entity);
            logger.info("Create Product Response: {}", response);
            return response;
        }
        throw new IllegalRequestBodyException();
    }

    @Transactional
    public ProductBO updateProduct(HttpHeaders headers, UUID productId, ProductBO productBO) {
        if (productId != null && !productId.toString().isEmpty()) {
            logger.info("Update Request ProductId: {}", productId);
            var entity = this.productRepository.findById(productId).orElseThrow(
                    () -> new ProductNotFoundException(null, productId)
            );
            logger.info("Find Product: {}", entity);
            var modifiedById = customerUtils.findByCustomerId(headers);
            entity.setModifiedOn(LocalDateTime.now());
            entity.setModifiedById(modifiedById);
            if(productBO.name() != null && !productBO.name().isEmpty()){
                final var control = this.productRepository.existsByName(productBO.name());
                if(control)
                    throw new IllegalRequestBodyException();
            }
            entity.setName(productBO.name() != null && !productBO.name().isEmpty() ?
                    productBO.name() : entity.getName());
            entity.setDescription(productBO.description() != null && !productBO.description().isEmpty() ?
                    productBO.description() : entity.getDescription());
            entity.setPrice(productBO.price() != null && BigDecimal.ZERO.compareTo(productBO.price()) < 0 ?
                    productBO.price() : entity.getPrice());
            entity.setStockQuantity(productBO.stockQuantity() != null ?
                    productBO.stockQuantity() : entity.getStockQuantity());
            entity = this.productRepository.save(entity);
            final var response = productServiceMapper.convertToBO(entity);
            logger.info("Update Response: {}", response);
            return response;
        }
        throw new IllegalParameterException();
    }

    @Transactional
    public ProductBO deleteProduct(UUID productId) {
        if (productId != null && !productId.toString().isEmpty()) {
            logger.info("Delete Request ProductId: {}", productId);
            var entity = this.productRepository.findById(productId).orElseThrow(
                    () -> new ProductNotFoundException(null, productId)
            );
            this.productRepository.delete(entity);
            logger.info("Delete Product: {}", entity);
            var response = productServiceMapper.convertToBO(entity);
            return response;
        }
        throw new IllegalParameterException();
    }

    public List<ProductBO> findAllOrderByStockQuantity(Constants.OrderBy orderBy, int page, int size) {
        if (orderBy.equals(Constants.OrderBy.ASC))
            return findAllOrderByStockQuantityAsc(page, size);
        else
            return findAllOrderByStockQuantityDesc(page, size);
    }

    private List<ProductBO> findAllOrderByStockQuantityAsc(int page, int size) {
        final var pageRequest = PageRequest.of(page - 1, size);
        logger.info("Request page: {} | size: {}", page, size);
        final var entitys = this.productRepository.findAllByOrderByStockQuantityAsc(pageRequest);
        final var response = productServiceMapper.convertToBO(entitys.getContent());
        logger.info("Response size: {}", response.size());
        return response;
    }

    private List<ProductBO> findAllOrderByStockQuantityDesc(int page, int size) {
        final var pageRequest = PageRequest.of(page - 1, size);
        logger.info("Request page: {} | size: {}", page, size);
        final var entitys = this.productRepository.findAllByOrderByStockQuantityDesc(pageRequest);
        final var response = productServiceMapper.convertToBO(entitys.getContent());
        logger.info("Response size: {}", response.size());
        return response;
    }

    private boolean validateProductData(ProductBO productBO) {
        if (productBO.name() == null || productBO.name().isEmpty())
            return false;
        else if (BigDecimal.ZERO.compareTo(productBO.price()) > 0 || productBO.price() == null)
            return false;
        else if (productBO.stockQuantity() != null && productBO.stockQuantity() < 0)
            return false;
        return true;
    }


    private ProductBO findByProductId(UUID productId) {
        if (productId != null && !productId.toString().isEmpty()) {
            logger.info("Request product id: {}", productId);
            final var entity = this.productRepository.findById(productId)
                    .orElseThrow(
                            () -> new ProductNotFoundException(null, productId)
                    );
            final var response = productServiceMapper.convertToBO(entity);
            logger.info("Response find by product id: {}", response);
            return response;
        }
        throw new IllegalParameterException();
    }

    private ProductBO findByProductName(String productName) {
        if (productName != null && !productName.isEmpty()) {
            logger.info("Request product name: {}", productName);
            final var entity = this.productRepository.findByName(productName)
                    .orElseThrow(
                            () -> new ProductNotFoundException(productName, null)
                    );
            final var response = productServiceMapper.convertToBO(entity);
            logger.info("Response find by product name: {}", response);
            return response;
        }
        throw new IllegalParameterException();
    }

    protected void decreaseStockQuantity(Cart cart){
        cart.getItems().forEach(
                item -> {
                    int updatedStockQuantity = item.getProduct().getStockQuantity() - item.getQuantity();
                    item.getProduct().setStockQuantity(updatedStockQuantity);
                }
        );
        var products = cart.getItems().stream().map(CartItem::getProduct).toList();
        this.productRepository.saveAll(products);
    }
}
