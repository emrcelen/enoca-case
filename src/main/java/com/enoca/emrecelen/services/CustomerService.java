package com.enoca.emrecelen.services;

import com.enoca.emrecelen.exception.EmailAlreadyExistsException;
import com.enoca.emrecelen.exception.InvalidCredentialsException;
import com.enoca.emrecelen.model.entity.Cart;
import com.enoca.emrecelen.model.entity.Customer;
import com.enoca.emrecelen.model.enumaration.Constants;
import com.enoca.emrecelen.repositories.CustomerRepository;
import com.enoca.emrecelen.security.service.JwtService;
import com.enoca.emrecelen.services.mapper.CustomerServiceMapper;
import com.enoca.emrecelen.services.model.bo.CustomerBO;
import com.enoca.emrecelen.services.model.bo.LoginCustomerBO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class CustomerService {
    private final CartService cartService;
    private final JwtService jwtService;
    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;
    private final CustomerServiceMapper customerServiceMapper;
    private final Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

    public CustomerService(
            CartService cartService,
            JwtService jwtService,
            CustomerRepository customerRepository,
            PasswordEncoder passwordEncoder,
            CustomerServiceMapper customerServiceMapper) {
        this.cartService = cartService;
        this.jwtService = jwtService;
        this.customerRepository = customerRepository;
        this.passwordEncoder = passwordEncoder;
        this.customerServiceMapper = customerServiceMapper;
    }

    @Transactional
    public CustomerBO register(CustomerBO customerBO){
        logger.info("Create Customer Request Data: {}", customerBO);
        if(isCustomerEmailUnique(customerBO.email())){
            var convert = new Customer.Builder()
                    .email(customerBO.email())
                    .password(passwordEncoder.encode(customerBO.password()))
                    .role(Constants.Role.USER)
                    .createdOn(LocalDateTime.now())
                    .build();
            var entity = this.customerRepository.save(convert);
            var cart = cartService.createCartForCustomer(new Cart.Builder()
                    .customer(entity)
                    .build());
            entity.setCart(cart);
            entity = this.customerRepository.save(entity);
            var response = customerServiceMapper.convertToBO(entity);
            logger.info("Create Customer Response: {}", response);
            return response;
        }
        throw new EmailAlreadyExistsException(customerBO.email());
    }

    public LoginCustomerBO login(String email, String password){
        logger.info("Login Customer Request | email: {} | password: {}", email, password);
        var customer = this.customerRepository.findByEmail(email)
                .orElseThrow(InvalidCredentialsException::new);

        if(passwordEncoder.matches(password, customer.getPassword())){
            final var token = jwtService.generateToken(customer);
            logger.info("Token: {}", token);
            logger.info("Customer logged in successfully: {}", email);
            return new LoginCustomerBO(email, token);
        }
        throw new InvalidCredentialsException();

    }

    private boolean isCustomerEmailUnique(String email){
        boolean present = this.customerRepository.findByEmail(email).isPresent();;
        return !present;
    }
}
