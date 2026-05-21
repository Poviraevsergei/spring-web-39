package com.tms.services;

import com.tms.exceptions.AgeException;
import com.tms.exceptions.RegistrationException;
import com.tms.model.Security;
import com.tms.model.User;
import com.tms.model.dto.RegistrationRequestDTO;
import com.tms.repositories.SecurityRepository;
import com.tms.repositories.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import java.time.Duration;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@DisplayName("Тестирование SecurityService")
@ExtendWith(MockitoExtension.class)
public class SecurityServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private SecurityRepository securityRepository;

    @Mock
    private TransactionTemplate transactionTemplate;

    @InjectMocks
    private SecurityService securityService;

    @BeforeEach
    public void setUp() {
        when(transactionTemplate.execute(any())).then(invocation -> {
            TransactionCallback callback = invocation.getArgument(0);
            return callback.doInTransaction(null);
        });
    }

    @DisplayName("Регистрация - успешный сценарий")
    @Test
    public void registration_Success() throws RegistrationException {
        RegistrationRequestDTO registrationRequestDTO = new RegistrationRequestDTO();
        registrationRequestDTO.setFirstName("John");
        registrationRequestDTO.setLastName("Doe");
        registrationRequestDTO.setAge(30);
        registrationRequestDTO.setUsername("simple");
        registrationRequestDTO.setPassword("qwerty");
        registrationRequestDTO.setEmail("awp@gmail.com");

        User userSaved = new User();
        userSaved.setId(1);
        userSaved.setFirstName(registrationRequestDTO.getFirstName());
        userSaved.setLastName(registrationRequestDTO.getLastName());
        userSaved.setAge(registrationRequestDTO.getAge());
        userSaved.setEmail(registrationRequestDTO.getEmail());

        when(userRepository.saveUser(any(User.class))).thenReturn(userSaved);
        doNothing().when(securityRepository).saveSecurity(any(Security.class));

        User registrationResult = securityService.registration(registrationRequestDTO);

        Assertions.assertNotNull(registrationResult);
        Assertions.assertEquals(registrationRequestDTO.getFirstName(), registrationResult.getFirstName());
        Assertions.assertEquals(registrationRequestDTO.getLastName(), registrationResult.getLastName());
        Assertions.assertEquals(registrationRequestDTO.getAge(), registrationResult.getAge());
        Assertions.assertEquals(registrationRequestDTO.getEmail(), registrationResult.getEmail());
        Assertions.assertTimeout(Duration.ofMillis(2000), () -> {
            securityService.registration(registrationRequestDTO);
        });
        Assertions.assertDoesNotThrow(() -> {
            securityService.registration(registrationRequestDTO);
        });
        verify(userRepository, times(3)).saveUser(any(User.class));
        verify(securityRepository, times(3)).saveSecurity(any(Security.class));
    }

    @DisplayName("Регистрация - возраст меньше 18")
    @Test
    public void registration_AgeException() {
        RegistrationRequestDTO registrationRequestDTO = new RegistrationRequestDTO();
        registrationRequestDTO.setAge(10);

        Assertions.assertThrows(AgeException.class, () -> {
            securityService.registration(registrationRequestDTO);
        });
        verify(userRepository, times(0)).saveUser(any(User.class));
        verify(securityRepository, times(0)).saveSecurity(any(Security.class));
    }
}
