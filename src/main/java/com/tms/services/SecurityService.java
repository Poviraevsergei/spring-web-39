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
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Slf4j
@Service
public class SecurityService {
    private final SecurityRepository securityRepository;
    private final UserRepository userRepository;
    private final SessionFactory sessionFactory;

    private final UserMapper userMapper;
    private final SecurityMapper securityMapper;

    public Security getSecurityById(Integer id) {
        log.debug("IN SecurityService:getSecurityById");
        Security securityFromDatabase = securityRepository.getSecurityById(id);
        log.info("Result securityFromDatabase: {}", securityFromDatabase);
        log.debug("OUT SecurityService:getUserById");
        return securityFromDatabase;
    }

    public User registration(RegistrationRequestDTO registrationDto) throws RegistrationException {
        log.debug("IN SecurityService:registration");
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.getTransaction();
            transaction.begin();
            User user = userMapper.mapFromRegistrationRequestDTOToUser(registrationDto);
            user = userRepository.saveUser(user);
            log.info("User saved: {}", user);
            Security security = securityMapper.mapFromRegistrationRequestDTOToSecurity(registrationDto, user);
            securityRepository.saveSecurity(security);
            transaction.commit();
            log.info("User security added for user with id: {}", user.getId());
            return user;
        } catch (Exception e) {
            log.error("Exception in registration", e);
        }
        throw new RegistrationException();
    }
}
