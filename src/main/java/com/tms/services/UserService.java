package com.tms.services;

import com.tms.exceptions.UserUpdateException;
import com.tms.model.User;
import com.tms.model.dto.UserCreateRequestDTO;
import com.tms.model.dto.UserDTO;
import com.tms.model.dto.UserUpdateRequestDTO;
import com.tms.repositories.UserRepository;
import com.tms.util.UserMapper;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserMapper userMapper;
    private final UserRepository userRepository;

    public List<UserDTO> getAllUsers() {
        return userRepository.findAll();
    }

    public UserDTO getUserById(Integer id) {
        log.debug("IN UserService:getUserById");
        User userFromDatabase = userRepository.getUserById(id);
        log.info("Result getUserById: {}", userFromDatabase);
        log.debug("OUT UserService:getUserById");
        return userMapper.mapFromUserToUserDTO(userFromDatabase);
    }

    public UserDTO createUser(UserCreateRequestDTO userDto) {
        log.debug("IN UserService:createUser");
        User user = new User();
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setAge(userDto.getAge());
        user.setEmail(userDto.getEmail());
        user.setCreated(LocalDateTime.now());
        user.setUpdated(LocalDateTime.now());
        User savedUser = userRepository.saveUser(user);
        UserDTO result = userMapper.mapFromUserToUserDTO(savedUser);
        log.debug("OUT UserService:createUser");
        return result;
    }

    public UserDTO updateUser(UserUpdateRequestDTO userDto) throws UserUpdateException {
        log.debug("IN UserService:updateUser");
        boolean result = userRepository.updateUser(userDto);
        if (result) {
            log.info("User with id: {} updated", userDto.getId());
            UserDTO user = getUserById(userDto.getId());
            log.debug("OUT UserService:updateUser");
            return user;
        }
        throw new UserUpdateException("User update failed for userId=" + userDto.getId());
    }


    public boolean deleteUserById(Integer id) {
        log.debug("IN UserService:deleteUserById");
        boolean result = userRepository.deleteUserById(id);
        log.info("Delete user with id: {}, and result: {}", id, result);
        log.debug("OUT UserService:deleteUserById");
        return result;
    }
}
