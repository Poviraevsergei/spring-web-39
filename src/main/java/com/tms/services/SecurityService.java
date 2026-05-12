package com.tms.services;

import com.tms.exceptions.AgeException;
import com.tms.model.Role;
import com.tms.model.Security;
import com.tms.model.User;
import com.tms.model.dto.RegistrationRequestDTO;
import com.tms.model.dto.UserDTO;
import com.tms.repositories.SecurityRepository;
import com.tms.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

import java.time.LocalDateTime;

@Slf4j
@Service
public class SecurityService {
    private final SecurityRepository securityRepository;
    private final UserRepository userRepository;
    private final TransactionTemplate transactionTemplate;

    @Autowired
    public SecurityService(UserRepository userRepository, SecurityRepository securityRepository, TransactionTemplate transactionTemplate) {
        this.userRepository = userRepository;
        this.securityRepository = securityRepository;
        this.transactionTemplate = transactionTemplate;
    }

    public UserDTO registration(RegistrationRequestDTO registrationDto) {
        log.debug("IN SecurityService:registration");
        return transactionTemplate.execute(action -> {
            User user = new User();
            user.setFirstName(registrationDto.getFirstName());
            user.setLastName(registrationDto.getLastName());

            if (registrationDto.getAge() < 18) {
                throw new AgeException();
            }
            user.setAge(registrationDto.getAge());
            user.setEmail(registrationDto.getEmail());
            user.setCreated(LocalDateTime.now());
            user.setUpdated(LocalDateTime.now());
            User savedUser = userRepository.saveUser(user);
            log.info("User saved: {}", savedUser);

            Security security = new Security();
            security.setUsername(registrationDto.getUsername());
            security.setPassword(registrationDto.getPassword());
            security.setRole(Role.USER);
            security.setUserId(savedUser.getId());
            securityRepository.saveSecurity(security);
            log.info("User security added for user with id: {}", savedUser.getId());

            UserDTO userDto = new UserDTO();
            userDto.setId(savedUser.getId());
            userDto.setFirstName(savedUser.getFirstName());
            userDto.setLastName(savedUser.getLastName());
            userDto.setAge(savedUser.getAge());
            userDto.setEmail(savedUser.getEmail());
            log.info("User registered successfully: {}", userDto);
            return userDto;
        });
    }
}
