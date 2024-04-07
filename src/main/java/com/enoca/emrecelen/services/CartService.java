package com.enoca.emrecelen.services;

import com.enoca.emrecelen.exception.CartNotFoundException;
import com.enoca.emrecelen.exception.ExcessiveQuantityException;
import com.enoca.emrecelen.exception.IllegalParameterException;
import com.enoca.emrecelen.exception.IllegalRequestBodyException;
import com.enoca.emrecelen.exception.InsufficientStockException;
import com.enoca.emrecelen.exception.ProductNotFoundException;
import com.enoca.emrecelen.exception.ProductNotFoundInCartException;
import com.enoca.emrecelen.model.entity.Cart;
import com.enoca.emrecelen.model.entity.CartItem;
import com.enoca.emrecelen.model.entity.Customer;
import com.enoca.emrecelen.model.entity.Product;
import com.enoca.emrecelen.repositories.CartRepository;
import com.enoca.emrecelen.services.mapper.CartServiceMapper;
import com.enoca.emrecelen.services.model.bo.CartBO;
import com.enoca.emrecelen.utilities.CustomerUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.UUID;

@Service
public class CartService {
    private final CartRepository cartRepository;
    private final CartItemService cartItemService;
    private final ProductService productService;
    private final CartServiceMapper cartServiceMapper;
    private final CustomerUtils customerUtils;
    private final Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

    public CartService(
            CartRepository cartRepository,
            CartItemService cartItemService,
            ProductService productService,
            CartServiceMapper cartServiceMapper,
            CustomerUtils customerUtils
    ) {
        this.cartRepository = cartRepository;
        this.cartItemService = cartItemService;
        this.productService = productService;
        this.cartServiceMapper = cartServiceMapper;
        this.customerUtils = customerUtils;
    }

    protected Cart createCartForCustomer(Cart cart) {
        return this.cartRepository.save(cart);
    }

    public CartBO findByCart(HttpHeaders headers) {
        final var customer = customerUtils.findByCustomer(headers);
        if (customer.getCart().getId() != null && !customer.getCart().getId().toString().isEmpty()) {
            logger.info("Request cart id:  {}", customer.getCart().getId());
            final var entity = this.cartRepository.findById(customer.getCart().getId()).orElseThrow(
                    () -> new CartNotFoundException(customer.getCart().getId())
            );
            final var response = cartServiceMapper.convertToBO(entity);
            logger.info("Response cart by id: {}", response);
            return response;
        }
        throw new IllegalParameterException();
    }


    @Transactional
    public CartBO emptyCart(HttpHeaders headers) {
        final var customer = customerUtils.findByCustomer(headers);
        if (customer.getCart().getId() != null && !customer.getCart().getId().toString().isEmpty()) {
            logger.info("Request cart id:  {}", customer.getCart().getId());
            var entity = this.cartRepository.findById(customer.getCart().getId()).orElseThrow(
                    () -> new CartNotFoundException(customer.getCart().getId())
            );
            cartItemService.deleteAll(entity.getItems());
            entity.getItems().clear();
            entity.setTotalPrice(BigDecimal.ZERO);
            entity = this.cartRepository.save(entity);
            final var response = cartServiceMapper.convertToBO(entity);
            logger.info("Response empty cart: {}", response);
            return response;
        }
        throw new IllegalParameterException();
    }

    @Transactional
    public CartBO addProductToCart(HttpHeaders headers, UUID productId, int quantity) {
        final var customer = customerUtils.findByCustomer(headers);
        if (customer.getCart().getId() != null && !customer.getCart().getId().toString().isEmpty()) {
            logger.info("Request cart id:  {}", customer.getCart().getId());
            var entity = this.cartRepository.findById(customer.getCart().getId()).orElseThrow(
                    () -> new CartNotFoundException(customer.getCart().getId())
            );
            final var product = productService.findByProductIdForCartItem(productId);
            if(product.getStockQuantity() < quantity)
                throw new InsufficientStockException(product.getName());
            final var isProductAvailable = entity.getItems().stream()
                    .map(CartItem::getProduct)
                    .anyMatch(item -> item.getId().equals(product.getId()));
            if(isProductAvailable){
                var updateCartItem = entity.getItems().stream()
                        .filter(item -> item.getProduct().getId().equals(product.getId()))
                        .findFirst().get();
                entity.getItems().remove(updateCartItem);
                var updatePrice = updateCartItem.getProduct().getPrice().multiply(BigDecimal.valueOf(quantity));
                updateCartItem.setTotalItemPrice(updateCartItem.getTotalItemPrice().add(updatePrice));
                updateCartItem.setQuantity(updateCartItem.getQuantity() + quantity);
                updateCartItem = cartItemService.updateCartItem(updateCartItem);
                entity.getItems().add(updateCartItem);
            }else {
                final var cartItem = cartItemService.productConvertCartItem(entity, product, quantity);
                entity.getItems().add(cartItem);
            }
            final var totalPrice = entity.getItems().stream()
                    .map(CartItem::getTotalItemPrice)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            entity.setTotalPrice(totalPrice);
            addProductFromCartQuantityControl(entity, product);
            final var response = cartServiceMapper.convertToBO(entity);
            logger.info("Response cart: {}", response);
            return response;
        }
        throw new IllegalParameterException();
    }

    @Transactional
    public CartBO removeProductFromCart(HttpHeaders headers, UUID productId, int quantity){
        final var customer = customerUtils.findByCustomer(headers);
        if (customer.getCart().getId() != null && !customer.getCart().getId().toString().isEmpty()) {
            logger.info("Request cart id:  {}", customer.getCart().getId());
            var entity = this.cartRepository.findById(customer.getCart().getId()).orElseThrow(
                    () -> new CartNotFoundException(customer.getCart().getId())
            );
            final var product = productService.findByProductIdForCartItem(productId);
            var cartItem = entity.getItems().stream()
                    .filter(item -> item.getProduct().getId().equals(product.getId()))
                    .findFirst().orElseThrow(ProductNotFoundInCartException::new);
            if(cartItem.getQuantity() < quantity || cartItem.getQuantity() - quantity < 0)
                throw new ExcessiveQuantityException();
            else if(cartItem.getQuantity() - quantity == 0){
                entity.getItems().remove(cartItem);
                cartItemService.deleteCartItem(cartItem);
            }
            else{
                cartItem.setQuantity(cartItem.getQuantity() - quantity);
                final var price = cartItem.getProduct().getPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity()));
                cartItem.setTotalItemPrice(price);
            }
            final var totalPrice = entity.getItems().stream()
                    .map(CartItem::getTotalItemPrice)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            entity.setTotalPrice(totalPrice);
            final var response = cartServiceMapper.convertToBO(entity);
            logger.info("Response cart: {}", response);
            return response;
        }
        throw new IllegalParameterException();
    }

    protected void updateCart(Customer customer){
        var cartEntity = customer.getCart();
        var cartItems = cartEntity.getItems();
        cartItems = cartItemService.updateCartItem(cartItems);
        cartEntity.getItems().clear();
        cartEntity = this.cartRepository.save(cartEntity);
        cartItemService.saveCartItems(cartItems);
        cartEntity.setItems(cartItems);
        final var totalPrice = cartEntity.getItems().stream()
                .map(CartItem::getTotalItemPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        cartEntity.setTotalPrice(totalPrice);
        this.cartRepository.save(cartEntity);
    }


    private void addProductFromCartQuantityControl(Cart cart, Product product){
        final var quantity = cart.getItems().stream()
                .filter(item -> item.getProduct().getId().equals(product.getId()))
                .map(CartItem::getQuantity)
                .findFirst().get();
        if(quantity > product.getStockQuantity())
            throw new InsufficientStockException(product.getName());
    }
    private boolean validateCartData(CartBO cartBO) {
        return cartBO.items() != null && !cartBO.items().isEmpty();
    }
}
