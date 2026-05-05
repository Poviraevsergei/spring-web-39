package com.tms.controllers;

import com.tms.model.dto.RegistrationRequestDTO;
import com.tms.model.dto.UserDTO;
import com.tms.services.SecurityService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/security")
public class SecurityController {

    private final SecurityService securityService;

    @Autowired
    public SecurityController(SecurityService securityService) {
        this.securityService = securityService;
    }

    @Operation(summary = "Регистрация новых пользователей", description = "В этом эндпоинте пользователь может создать свой аккаунт в нашей системе. Для этого необходим .....")
    @PostMapping("/registration")
    public ResponseEntity<UserDTO> registration(
            @RequestBody @Valid RegistrationRequestDTO registrationDto,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        UserDTO createdUser = securityService.registration(registrationDto);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }
}