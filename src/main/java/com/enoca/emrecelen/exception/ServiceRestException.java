package com.enoca.emrecelen.exception;

import com.enoca.emrecelen.exception.enumaration.ServiceManageError;

import java.util.Map;

public class ServiceRestException extends RuntimeException {
    private final ServiceManageError managerError;
    private final String description;
    private final Map<String, String> errors;

    public ServiceRestException(ServiceManageError managerError, String description, Map<String, String> errors) {
        super(managerError.name());
        this.managerError = managerError;
        this.description = description;
        this.errors = errors;
    }

    public ServiceManageError getManagerError() {
        return managerError;
    }

    public String getDescription() {
        return description;
    }

    public Map<String, String> getErrors() {
        return errors;
    }
}
