package com.tms.controllers;

import com.tms.model.dto.UserDto;
import com.tms.services.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

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
            @RequestParam(value = "firstName") String firstName,
            @RequestParam(value = "lastName") String lastName,
            @RequestParam(value = "age") int age,
            @RequestParam(value = "username") String username,
            @RequestParam(value = "email") String email,
            @RequestParam(value = "password") String password,
            ModelAndView model) {
        try {
            UserDto createdUser = securityService.registration(firstName, lastName, age, username, password, email);
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
