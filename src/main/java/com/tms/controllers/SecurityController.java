package com.tms.controllers;

import com.tms.model.dto.RegistrationRequestDto;
import com.tms.model.dto.UserDto;
import com.tms.services.SecurityService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.stream.Collectors;

@Controller
@RequestMapping("/security")
public class SecurityController {

    private final SecurityService securityService;

    @Autowired
    public SecurityController(SecurityService securityService) {
        this.securityService = securityService;
    }

    @PostMapping("/registration")
    public ModelAndView registration(
            @ModelAttribute @Valid RegistrationRequestDto registrationDto,
            BindingResult bindingResult,
            ModelAndView model) {
        try {
            if (bindingResult.hasErrors()) {
                System.out.println(bindingResult.getAllErrors());
                model.addObject("exception", bindingResult.getAllErrors());
                model.setViewName("error-registration");
                model.setStatus(HttpStatus.BAD_REQUEST);
                return model;
            }
            UserDto createdUser = securityService.registration(registrationDto);
            model.addObject("user", createdUser);
            model.setViewName("success-registration");
            model.setStatus(HttpStatus.CREATED); //201 CREATED
            return model;
        } catch (Exception exception) {
            model.setViewName("error-registration");
            model.addObject("exception", exception.getMessage());
            model.setStatus(HttpStatus.INTERNAL_SERVER_ERROR); //500
            return model;
        }
    }
}
