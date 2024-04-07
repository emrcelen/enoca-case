package com.enoca.emrecelen.security.service;

import com.enoca.emrecelen.exception.InvalidCredentialsException;
import com.enoca.emrecelen.repositories.CustomerRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class UserDetailService implements UserDetailsService {
    private final CustomerRepository customerRepository;

    public UserDetailService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        final var customer = this.customerRepository.findByEmail(username)
                .orElseThrow(InvalidCredentialsException::new);
        return customer;
    }
}
