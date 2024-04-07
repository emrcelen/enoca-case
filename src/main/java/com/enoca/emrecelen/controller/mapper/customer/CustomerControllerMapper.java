package com.enoca.emrecelen.controller.mapper.customer;

import com.enoca.emrecelen.controller.dto.request.customer.RegisterCustomerRequestDTO;
import com.enoca.emrecelen.controller.dto.response.customer.LoginCustomerResponseDTO;
import com.enoca.emrecelen.controller.dto.response.customer.RegisterCustomerResponseDTO;
import com.enoca.emrecelen.services.model.bo.CustomerBO;
import com.enoca.emrecelen.services.model.bo.LoginCustomerBO;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class CustomerControllerMapper {

    public CustomerBO convertRegisterCustomerToBO(RegisterCustomerRequestDTO request) {
        return new CustomerBO(
                null,
                request.email(),
                request.password(),
                null,
                null,
                null,
                LocalDateTime.now(),
                null
        );
    }

    public RegisterCustomerResponseDTO convertBOToRegisterCustomerResponse(CustomerBO customerBO){
        return new RegisterCustomerResponseDTO(
                customerBO.email(),
                customerBO.role().name()
        );
    }

    public LoginCustomerResponseDTO convertToBOLoginCustomerResponse(LoginCustomerBO loginCustomerBO){
        return new LoginCustomerResponseDTO(
                loginCustomerBO.email(),
                loginCustomerBO.token()
        );
    }
}
