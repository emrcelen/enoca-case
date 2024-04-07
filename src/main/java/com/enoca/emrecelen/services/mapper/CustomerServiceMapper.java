package com.enoca.emrecelen.services.mapper;

import com.enoca.emrecelen.model.entity.Customer;
import com.enoca.emrecelen.model.enumaration.Constants;
import com.enoca.emrecelen.services.model.bo.CustomerBO;
import com.enoca.emrecelen.services.model.enumaration.Role;
import org.springframework.stereotype.Component;

@Component
public class CustomerServiceMapper {
    private final OrderServiceMapper orderServiceMapper;

    public CustomerServiceMapper(OrderServiceMapper orderServiceMapper) {
        this.orderServiceMapper = orderServiceMapper;
    }

    public CustomerBO convertToBO(Customer customer) {
        return new CustomerBO(
                customer.getId(),
                customer.getEmail(),
                customer.getPassword(),
                convertToRole(customer.getRole()),
                customer.getCart() != null ? customer.getCart().getId() : null,
                !customer.getOrders().isEmpty() ? orderServiceMapper.convertToUUID(customer.getOrders())
                        : null,
                customer.getCreatedOn(),
                customer.getModifiedOn()
        );
    }

    private Role convertToRole(Constants.Role role) {
        return switch (role) {
            case USER -> Role.USER;
            case PREMIUM_USER -> Role.PREMIUM_USER;
            case ADMIN -> Role.ADMIN;
        };
    }

    private Constants.Role convertToRole(Role role) {
        return switch (role) {
            case USER -> Constants.Role.USER;
            case PREMIUM_USER -> Constants.Role.PREMIUM_USER;
            case ADMIN -> Constants.Role.ADMIN;
        };
    }
}
