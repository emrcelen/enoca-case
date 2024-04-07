package com.enoca.emrecelen.services;

import com.enoca.emrecelen.exception.IllegalParameterException;
import com.enoca.emrecelen.exception.OrderNotFoundException;
import com.enoca.emrecelen.exception.ProductPriceChangeException;
import com.enoca.emrecelen.exception.UnexpectedException;
import com.enoca.emrecelen.model.entity.Customer;
import com.enoca.emrecelen.model.entity.Order;
import com.enoca.emrecelen.repositories.OrderRepository;
import com.enoca.emrecelen.services.mapper.OrderServiceMapper;
import com.enoca.emrecelen.services.model.bo.OrderBO;
import com.enoca.emrecelen.utilities.CustomerUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.UUID;
import java.util.List;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderServiceMapper orderServiceMapper;
    private final OrderItemService orderItemService;
    private final CartService cartService;
    private final ProductService productService;
    private final CustomerUtils customerUtils;
    private final Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

    public OrderService(
            OrderRepository orderRepository,
            OrderServiceMapper orderServiceMapper,
            OrderItemService orderItemService,
            CartService cartService, ProductService productService, CustomerUtils customerUtils
    ) {
        this.orderRepository = orderRepository;
        this.orderServiceMapper = orderServiceMapper;
        this.orderItemService = orderItemService;
        this.cartService = cartService;
        this.productService = productService;
        this.customerUtils = customerUtils;
    }


    public OrderBO placeOrder(HttpHeaders headers){
        final var customer = customerUtils.findByCustomer(headers);
        final var customerCart = customer.getCart();
        logger.info("Order Customer: {} | Cart Id: {}",customer.getId(), customerCart.getId());
        var entity = firstOrderCreate(customer);
        entity = this.orderRepository.save(entity);
        if(customerCart.getItems().isEmpty())
            throw new UnexpectedException();
        final var orderItems = orderItemService.createOrderItems(customerCart.getItems(), entity);
        entity.setOrderItems(orderItems);
        final var orderTotalPrice = entity.getOrderItems().stream()
                .map(item -> item.getUnitPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        if(!orderTotalPrice.equals(customerCart.getTotalPrice())){
            if(orderTotalPrice.compareTo(customerCart.getTotalPrice()) < 0)
                logger.info("Discounted order placed by customer with Id {} for total amount of ${}", customer.getId(), orderTotalPrice);
            else{
                cartService.updateCart(customer);
                this.orderRepository.delete(entity);
                throw new ProductPriceChangeException();
            }
        }
        entity.setTotalPrice(orderTotalPrice);
        entity = this.orderRepository.save(entity);
        final var response = orderServiceMapper.convertToBO(entity);
        logger.info("Response: {}", response);
        productService.decreaseStockQuantity(customerCart);
        cartService.emptyCart(headers);
        return response;
    }

    public OrderBO getOrderForCode(HttpHeaders headers, UUID orderId){
        final var customer = customerUtils.findByCustomer(headers);
        if(orderId != null && !orderId.toString().isEmpty()){
            logger.info("Request order by id: {}", orderId);
            final var entity = this.orderRepository.findById(orderId).orElseThrow(
                    () -> new OrderNotFoundException(orderId)
            );
            if(!customer.getOrders().contains(entity))
                throw new OrderNotFoundException(orderId);
            final var response = orderServiceMapper.convertToBO(entity);
            logger.info("Response Order by id: {}", response);
            return response;
        }
        throw new IllegalParameterException();
    }

    public List<OrderBO> getAllOrdersForCustomer(HttpHeaders headers){
        final var customer = customerUtils.findByCustomer(headers);
        logger.info("Request customer: {}", customer.getEmail());
        final var orders = customer.getOrders();
        final var response = orderServiceMapper.convertToBO(orders);
        logger.info("Response size: {}", response.size());
        return response;
    }

    private Order firstOrderCreate(Customer customer) {
        final var entity  = new Order.Builder()
                .customer(customer)
                .createdById(customer.getId())
                .build();
        return entity;
    }
}
