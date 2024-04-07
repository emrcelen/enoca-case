package com.enoca.emrecelen.controller;

import com.enoca.emrecelen.controller.dto.request.customer.LoginCustomerRequestDTO;
import com.enoca.emrecelen.controller.dto.request.customer.RegisterCustomerRequestDTO;
import com.enoca.emrecelen.controller.mapper.RestResponseMapper;
import com.enoca.emrecelen.controller.mapper.customer.CustomerControllerMapper;
import com.enoca.emrecelen.controller.swagger.CustomerControllerSwagger;
import com.enoca.emrecelen.services.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CustomerController implements CustomerControllerSwagger {
    private final CustomerService customerService;
    private final CustomerControllerMapper customerControllerMapper;

    public CustomerController(CustomerService customerService, CustomerControllerMapper customerControllerMapper) {
        this.customerService = customerService;
        this.customerControllerMapper = customerControllerMapper;
    }

    @Override
    public ResponseEntity<?> register(RegisterCustomerRequestDTO request) {
        final var requestBO = customerControllerMapper.convertRegisterCustomerToBO(request);
        final var saveEntity = this.customerService.register(requestBO);
        final var responseDTO = customerControllerMapper.convertBOToRegisterCustomerResponse(saveEntity);
        final var response = RestResponseMapper.convertToSuccessResponse(responseDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Override
    public ResponseEntity<?> login(LoginCustomerRequestDTO request) {
        final var loginEntity = this.customerService.login(request.email(), request.password());
        final var responseDTO = customerControllerMapper.convertToBOLoginCustomerResponse(loginEntity);
        final var response = RestResponseMapper.convertToSuccessResponse(responseDTO);
        return ResponseEntity.ok(response);
    }
}
