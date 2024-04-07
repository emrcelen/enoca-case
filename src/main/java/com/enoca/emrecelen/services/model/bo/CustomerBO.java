package com.enoca.emrecelen.services.model.bo;

import com.enoca.emrecelen.services.model.enumaration.Role;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.List;

public record CustomerBO(
        UUID id,
        String email,
        String password,
        Role role,
        UUID cart,
        List<UUID> orders,
        LocalDateTime createdOn,
        LocalDateTime modifiedOn
) {
}
