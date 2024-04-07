package com.enoca.emrecelen.exception.enumaration;

import org.springframework.http.HttpStatus;

public enum ServiceManageError {
    BAD_PARAM(HttpStatus.BAD_REQUEST, 830001),
    BAD_PARAM_BODY(HttpStatus.BAD_REQUEST, 830002),
    BAD_PARAM_VALIDATION(HttpStatus.BAD_REQUEST, 830003),

    BAD_CREDENTIALS(HttpStatus.BAD_REQUEST, 830101),

    EMAIL_ADDRESS_IS_ALREADY_IN_USE(HttpStatus.BAD_REQUEST, 830201),

    PRODUCT_NOT_FOUND_BY_ID(HttpStatus.NOT_FOUND, 830301),
    PRODUCT_NOT_FOUND_BY_NAME(HttpStatus.NOT_FOUND, 830302),
    PRODUCT_INSUFFICENT_STOCK(HttpStatus.BAD_REQUEST, 830303),
    PRODUCT_PRICE_CHANGE_TYPE_INCREASED(HttpStatus.FORBIDDEN, 830304),
    PRODUCT_NOT_FOUND_IN_CART(HttpStatus.NOT_FOUND, 830305),

    CART_NOT_FOUND_BY_ID(HttpStatus.NOT_FOUND, 830401),
    CART_EXESSIVE_QUANTITY(HttpStatus.BAD_REQUEST, 830402),

    ORDER_NOT_FOUND_BY_ID(HttpStatus.NOT_FOUND, 830501),

    MISSING_AUTHORIZATION_HEADER(HttpStatus.UNAUTHORIZED, 930001),
    UNEXPECTED(HttpStatus.I_AM_A_TEAPOT, 930002);

    private final HttpStatus status;
    private final int code;

    ServiceManageError(HttpStatus status, int code) {
        this.status = status;
        this.code = code;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public int getCode() {
        return code;
    }
}
