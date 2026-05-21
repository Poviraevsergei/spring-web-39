package com.tms.controllers;

import com.tms.exceptions.UserNotFoundException;
import com.tms.exceptions.UserUpdateException;
import com.tms.model.User;
import com.tms.model.dto.UserCreateRequestDTO;
import com.tms.model.dto.UserUpdateRequestDTO;
import com.tms.services.UserService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }

    @ApiResponses(value = {
            @ApiResponse(description = "Пользователь с таким id не найден", responseCode = "404"),
            @ApiResponse(description = "Пользователь успешно найден", responseCode = "200"),
            @ApiResponse(description = "Ошибка сервера", responseCode = "500"),
            @ApiResponse(description = "Общая ошибка запроса", responseCode = "400")
    })
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable @Parameter(description = "Уникальный идентификатор пользователя", example = "67") Integer id) {
        User user = userService.getUserById(id);
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody @Valid UserCreateRequestDTO userRequest) {
        User createdUser = userService.createUser(userRequest);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<User> updateUser(@RequestBody @Valid UserUpdateRequestDTO userRequest) throws UserUpdateException, UserNotFoundException {
        User user = userService.updateUser(userRequest);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteUserById(@PathVariable Integer id) throws UserNotFoundException {
        userService.deleteUserById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
