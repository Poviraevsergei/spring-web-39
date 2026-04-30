package com.tms.services;

import com.tms.exceptions.UserUpdateException;
import com.tms.model.User;
import com.tms.model.dto.UserCreateRequestDTO;
import com.tms.model.dto.UserDTO;
import com.tms.model.dto.UserUpdateRequestDTO;
import com.tms.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserDTO> getAllUsers() {
        return userRepository.findAll();
    }

    public UserDTO getUserById(Integer id) {
        return userRepository.getUserById(id);
    }

    public UserDTO createUser(UserCreateRequestDTO userDto) {
        User user = new User();
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setAge(userDto.getAge());
        user.setEmail(userDto.getEmail());
        user.setCreated(LocalDateTime.now());
        user.setUpdated(LocalDateTime.now());
        User savedUser = userRepository.saveUser(user);
        return getUserById(savedUser.getId());
    }

    public UserDTO updateUser(UserUpdateRequestDTO userDto) throws UserUpdateException {
        boolean result = userRepository.updateUser(userDto);
        if (result) {
            return getUserById(userDto.getId());
        }
        throw new UserUpdateException("User update failed for userId=" + userDto.getId());
    }

    public boolean deleteUserById(Integer id) {
        return userRepository.deleteUserById(id);
    }
}
