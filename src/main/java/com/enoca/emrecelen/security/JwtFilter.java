package com.enoca.emrecelen.security;

import com.enoca.emrecelen.exception.MissingAuthorizationHeaderException;
import com.enoca.emrecelen.exception.UnexpectedException;
import com.enoca.emrecelen.repositories.CustomerRepository;
import com.enoca.emrecelen.utilities.CustomerUtils;
import com.enoca.emrecelen.utilities.FilterUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {
    private final CustomerRepository customerRepository;
    private final CustomerUtils customerUtils;

    public JwtFilter(
            CustomerRepository customerRepository,
            CustomerUtils customerUtils
    ) {
        this.customerRepository = customerRepository;
        this.customerUtils = customerUtils;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final var path = request.getRequestURI();
        if(FilterUtils.isExcludePath(path)) {
            filterChain.doFilter(request, response);
            return;
        }
        final var authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if(authorizationHeader == null || !authorizationHeader.startsWith("Bearer")) {
            filterChain.doFilter(request, response);
            return;
        }
        final var token = authorizationHeader.substring(7);
        final var customerId = customerUtils.findByCustomerId(token);
        if(customerId != null && SecurityContextHolder.getContext().getAuthentication() == null){
            final var customerDetails = customerRepository.findById(customerId).get();
            final var authenticationToken = new UsernamePasswordAuthenticationToken(customerDetails, customerDetails.getRole().name(), customerDetails.getAuthorities());
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            filterChain.doFilter(request, response);
            return;
        }
        throw new UnexpectedException();
    }
}
