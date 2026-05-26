package com.tms.services;

import com.tms.exceptions.RegistrationException;
import com.tms.model.Security;
import com.tms.model.User;
import com.tms.model.dto.RegistrationRequestDTO;
import com.tms.repositories.SecurityRepository;
import com.tms.repositories.UserRepository;
import com.tms.util.SecurityMapper;
import com.tms.util.UserMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
@Service
public class SecurityService {
    private final SecurityRepository securityRepository;
    private final UserRepository userRepository;

    private final UserMapper userMapper;
    private final SecurityMapper securityMapper;

    public Optional<Security> getSecurityById(Integer id) {
        log.debug("IN SecurityService:getSecurityById");
        Optional<Security> securityFromDatabase = securityRepository.findById(id);
        log.info("Result securityFromDatabase: {}", securityFromDatabase);
        log.debug("OUT SecurityService:getUserById");
        return securityFromDatabase;
    }

    @Transactional(rollbackFor = Exception.class,
            timeout = 30,
            isolation = Isolation.READ_UNCOMMITTED,
            propagation = Propagation.REQUIRED)
    public User registration(RegistrationRequestDTO registrationDto) throws RegistrationException {
        log.debug("IN SecurityService:registration");
        if (securityRepository.existsByUsername(registrationDto.getUsername()) ||
                userRepository.existsByEmail(registrationDto.getEmail())) {
            throw new RegistrationException("Username/Email already exists");
        }
        User user = userMapper.mapFromRegistrationRequestDTOToUser(registrationDto);
        user = userRepository.save(user);
        log.info("User saved: {}", user);
        Security security = securityMapper.mapFromRegistrationRequestDTOToSecurity(registrationDto, user);
        securityRepository.save(security);
        log.info("User security added for user with id: {}", user.getId());
        return user;
    }
}
