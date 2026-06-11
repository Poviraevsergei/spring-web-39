package com.tms.services;

import com.tms.exceptions.RegistrationException;
import com.tms.model.Security;
import com.tms.model.User;
import com.tms.model.dto.AuthRequestDTO;
import com.tms.model.dto.AuthResponseDTO;
import com.tms.model.dto.RegistrationRequestDTO;
import com.tms.repositories.SecurityRepository;
import com.tms.repositories.UserRepository;
import com.tms.util.SecurityMapper;
import com.tms.util.UserMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    private final JwtService jwtService;
    private final UserMapper userMapper;
    private final SecurityMapper securityMapper;
    private final PasswordEncoder passwordEncoder;

    public Optional<Security> getSecurityById(Integer id) {
        log.debug("IN SecurityService:getSecurityById");
        Optional<Security> securityFromDatabase = securityRepository.findById(id);
        log.info("Result securityFromDatabase: {}", securityFromDatabase);
        log.debug("OUT SecurityService:getUserById");
        return securityFromDatabase;
    }

    public Optional<AuthResponseDTO> generateJWT(AuthRequestDTO authRequestDTO) {
        log.debug("IN SecurityService:generateJWT");
        Optional<Security> securityOptional = securityRepository.findByUsername(authRequestDTO.getUsername());
        if (securityOptional.isEmpty()) {
            throw new UsernameNotFoundException("Username not found: " + authRequestDTO.getUsername());
        }
        Security security = securityOptional.get();
        if (!passwordEncoder.matches(authRequestDTO.getPassword(), security.getPassword())) {
            return Optional.empty();
        }
        String jwt = jwtService.generateJwt(security.getUsername());
        if (jwt == null) {
            return Optional.empty();
        }
        log.debug("OUT SecurityService:generateJWT");
        return Optional.of(new AuthResponseDTO(jwt));
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
        security.setPassword(passwordEncoder.encode(security.getPassword()));
        securityRepository.save(security);
        log.info("User security added for user with id: {}", user.getId());
        log.debug("OUT SecurityService:registration");
        return user;
    }
}
