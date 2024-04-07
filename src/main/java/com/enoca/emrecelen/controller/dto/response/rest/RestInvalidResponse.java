package com.enoca.emrecelen.controller.dto.response.rest;

import com.enoca.emrecelen.exception.enumaration.ServiceManageError;
import com.fasterxml.jackson.annotation.JsonInclude;

public record RestInvalidResponse <T>(
        String errorMessage,
        @JsonInclude(JsonInclude.Include.NON_NULL)
        T errors,
        int errorCode,
        String detail,
        String errorDate
) {
}