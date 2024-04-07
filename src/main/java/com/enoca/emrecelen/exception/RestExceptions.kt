package com.enoca.emrecelen.exception

import com.enoca.emrecelen.exception.enumaration.ServiceManageError
import java.util.UUID

class IllegalParameterException() :
    ServiceRestException(
        ServiceManageError.BAD_PARAM,
        "The provided parameter is invalid. Please ensure that the parameter meets the required criteria and try again.",
        null
    )

class IllegalRequestBodyException() :
    ServiceRestException(
        ServiceManageError.BAD_PARAM_BODY,
        "The request body contains invalid data. Please check your input and try again.",
        null
    )

class MissingAuthorizationHeaderException() :
    ServiceRestException(
        ServiceManageError.MISSING_AUTHORIZATION_HEADER,
        "Authorization header is missing. Please include the Authorization header in your request and try again.",
        null
    )

class UnexpectedException() :
    ServiceRestException(
        ServiceManageError.UNEXPECTED,
        "An unexpected error occurred. Please try again later or contact support for assistance.",
        null
    )

class InvalidCredentialsException() :
    ServiceRestException(
        ServiceManageError.BAD_CREDENTIALS,
        "Invalid email or password",
        null
    )

class EmailAlreadyExistsException(email: String) :
    ServiceRestException(
        ServiceManageError.EMAIL_ADDRESS_IS_ALREADY_IN_USE,
        "This email address is already in use, please try another email address.",
        mapOf(
            "email" to email
        )
    )

class ProductNotFoundException(productName: String?, productId: UUID?) :
    ServiceRestException(
        if (productName != null && productId == null) ServiceManageError.PRODUCT_NOT_FOUND_BY_NAME else ServiceManageError.PRODUCT_NOT_FOUND_BY_ID,
        if (productName != null && productId == null) "The product you are looking for could not be found. Please check the product name and try again."
        else "The product you are looking for could not be found. Please check the product ID and try again.",
        if (productName != null && productId == null) mapOf("productName" to productName) else mapOf("productId" to productId.toString())
    )

class CartNotFoundException(cartId: UUID) :
    ServiceRestException(
        ServiceManageError.CART_NOT_FOUND_BY_ID,
        "The cart you are looking for could not be found. Please check the cart ID and try again.",
        mapOf("cart" to cartId.toString())
    )

class InsufficientStockException(productName: String) :
    ServiceRestException(
        ServiceManageError.PRODUCT_INSUFFICENT_STOCK,
        "Not enough stock for product: $productName",
        null
    )

class ExcessiveQuantityException() :
    ServiceRestException(
        ServiceManageError.CART_EXESSIVE_QUANTITY,
        "Excessive quantity. Attempted to remove more items than available in the cart.",
        null
    )

class ProductPriceChangeException() :
    ServiceRestException(
        ServiceManageError.PRODUCT_PRICE_CHANGE_TYPE_INCREASED,
        "The price of one or more products in your cart has increased since you last viewed them. Please review your order before proceeding.",
        null
    )

class OrderNotFoundException(orderId: UUID) :
    ServiceRestException(
        ServiceManageError.ORDER_NOT_FOUND_BY_ID,
        "The order you are looking for could not be found. Please check the order ID and try again.",
        mapOf("order" to orderId.toString())
    )

class ProductNotFoundInCartException() :
    ServiceRestException(
        ServiceManageError.PRODUCT_NOT_FOUND_IN_CART,
        "Sorry, we couldn't find the item you're looking for in your cart. Please make sure you've added a valid item.",
        null
    )