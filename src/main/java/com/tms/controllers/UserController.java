package com.tms.controllers;

import com.tms.model.User;
import com.tms.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ModelAndView getAllUsers(ModelAndView model) {
        List<User> users = userService.getAllUsers();
        model.addObject("users", users);
        model.setViewName("users-page");
        model.setStatus(HttpStatus.OK);
        return model;
    }
}
