package com.tms.services;

import com.tms.model.dto.UserDto;
import com.tms.repositories.SecurityRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SecurityService {
    private final SecurityRepository securityRepository;

    public SecurityService(SecurityRepository securityRepository) {
        this.securityRepository = securityRepository;
    }

    public Optional<UserDto> registration(String firstName, String second, int age, String username, String password) {
        //TODO: JDBC Template, parse User to UserDto
        return null;
    }
}
