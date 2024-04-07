package com.enoca.emrecelen.utilities;


import com.enoca.emrecelen.model.entity.Customer;
import com.enoca.emrecelen.repositories.CustomerRepository;
import com.enoca.emrecelen.security.service.JwtService;

import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public final class CustomerUtils {
    private final CustomerRepository customerRepository;
    private final JwtService jwtService;

    public CustomerUtils(
            CustomerRepository customerRepository,
            JwtService jwtService) {
        this.customerRepository = customerRepository;
        this.jwtService = jwtService;
    }

    private String findByCustomerEmail(HttpHeaders headers){
        return jwtService.findByCustomerEmail(headers);
    }

    public UUID findByCustomerId(HttpHeaders headers){
        final var email = findByCustomerEmail(headers);
        return customerRepository.findByEmail(email).get().getId();
    }

    public Customer findByCustomer(HttpHeaders headers){
        final var email = findByCustomerEmail(headers);
        return customerRepository.findByEmail(email).get();
    }

    public UUID findByCustomerId(String token){
        final var email = jwtService.findByCustomerEmail(token);
        return customerRepository.findByEmail(email).get().getId();
    }
}
