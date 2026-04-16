package com.tms.services;

import com.tms.model.Role;
import com.tms.model.Security;
import com.tms.model.User;
import com.tms.model.dto.RegistrationRequestDto;
import com.tms.model.dto.UserDto;
import com.tms.repositories.SecurityRepository;
import com.tms.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

import java.time.LocalDateTime;
import java.util.Optional;

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

    public UserDto registration(RegistrationRequestDto registrationDto) {
        return transactionTemplate.execute(action -> {
            User user = new User();
            user.setFirstName(registrationDto.getFirstName());
            user.setLastName(registrationDto.getLastName());
            user.setAge(registrationDto.getAge());
            user.setEmail(registrationDto.getEmail());
            user.setCreated(LocalDateTime.now());
            user.setUpdated(LocalDateTime.now());
            User savedUser = userRepository.saveUser(user);

            Security security = new Security();
            security.setUsername(registrationDto.getUsername());
            security.setPassword(registrationDto.getPassword());
            security.setRole(Role.USER);
            security.setUserId(savedUser.getId());
            securityRepository.saveSecurity(security);

            UserDto userDto = new UserDto();
            userDto.setId(savedUser.getId());
            userDto.setFirstName(savedUser.getFirstName());
            userDto.setLastName(savedUser.getLastName());
            userDto.setAge(savedUser.getAge());
            return userDto;
        });
    }
}
