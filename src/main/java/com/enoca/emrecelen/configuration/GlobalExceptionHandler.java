package com.enoca.emrecelen.configuration;

import com.enoca.emrecelen.controller.dto.response.rest.RestInvalidResponse;
import com.enoca.emrecelen.controller.dto.response.rest.RestResponse;
import com.enoca.emrecelen.controller.mapper.RestResponseMapper;
import com.enoca.emrecelen.exception.ServiceRestException;
import com.enoca.emrecelen.exception.enumaration.ServiceManageError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ServiceRestException.class)
    private ResponseEntity<?> handleServiceRestException(ServiceRestException ex, WebRequest request){
        final var message = ex.getDescription();
        final var errors = ex.getErrors();
        final var errorCode = ex.getManagerError().getCode();
        final var detail = request.getDescription(false);
        return getResponse(message, detail, errorCode, errors, ex.getManagerError().getStatus());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    private ResponseEntity<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex, WebRequest request){
        final var message = ex.getTypeMessageCode();
        final var detail = request.getDescription(false);
        final var errors = new HashMap<String, String>();
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            errors.put(error.getField(), error.getDefaultMessage());
        });
        final var errorCode =ServiceManageError.BAD_PARAM_VALIDATION.getCode();
        return getResponse(message, detail, errorCode, errors, ServiceManageError.BAD_PARAM_VALIDATION.getStatus());
    }

    private ResponseEntity<?> getResponse(String message, String detail, int errorCode, Map<String, String> errors, HttpStatus httpStatus){
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        RestInvalidResponse response = new RestInvalidResponse(
                message,
                errors,
                errorCode,
                detail,
                formatter.format(LocalDateTime.now())
        );
        RestResponse<RestInvalidResponse> restResponse = RestResponseMapper.convertToNotSuccessResponse(response);
        return new ResponseEntity<>(restResponse, httpStatus);
    }
}
