package com.tms.util;

import com.tms.model.Role;
import com.tms.model.Security;
import com.tms.model.User;
import com.tms.model.dto.RegistrationRequestDTO;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class SecurityMapper {
    public Security mapFromRegistrationRequestDTOToSecurity(RegistrationRequestDTO userDTO, User user) {
        if (userDTO == null) {
            throw new NullPointerException();
        }
        Security security = new Security();
        security.setUsername(userDTO.getUsername());
        security.setPassword(userDTO.getPassword());
        security.setRole(Role.USER);
        security.setCreated(LocalDateTime.now());
        security.setUpdated(LocalDateTime.now());
        return security;
    }
}
