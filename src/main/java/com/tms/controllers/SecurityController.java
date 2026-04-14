package com.tms.controllers;

import com.tms.model.dto.UserDto;
import com.tms.services.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Controller
@RequestMapping("/security") //Перед всеми путями методов этого контроллера будет стоять /security
public class SecurityController {

    private final SecurityService securityService;

    @Autowired
    public SecurityController(SecurityService securityService) {
        this.securityService = securityService;
    }

    @PostMapping("/registration")
    public ModelAndView registration(
            @RequestParam(value = "firstName") String firstName,
            @RequestParam(value = "secondName") String secondName,
            @RequestParam(value = "age") int age,
            @RequestParam(value = "username") String username,
            @RequestParam(value = "password") String password,
            ModelAndView model) {
        Optional<UserDto> createdUserOptional = securityService.registration(firstName, secondName, age, username, password);

        if (createdUserOptional.isPresent()) {
            UserDto createdUser = createdUserOptional.get();
            model.addObject("firstName", createdUser.getFirstName());
            model.addObject("secondName", createdUser.getSecondName());
            model.addObject("age", createdUser.getAge());
            model.setViewName("success-registration");
            model.setStatus(HttpStatus.CREATED);
            return model;
        }

        model.setViewName("error-registration");
        model.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        return model;
    }
}
