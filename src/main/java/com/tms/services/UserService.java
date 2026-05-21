package com.tms.services;

import com.tms.exceptions.UserNotFoundException;
import com.tms.exceptions.UserUpdateException;
import com.tms.model.User;
import com.tms.model.dto.UserCreateRequestDTO;
import com.tms.model.dto.UserUpdateRequestDTO;
import com.tms.repositories.UserRepository;
import com.tms.util.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserMapper userMapper;
    private final UserRepository userRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Integer id) {
        log.debug("IN UserService:getUserById");
        User userFromDatabase = userRepository.getUserById(id);
        log.debug("OUT UserService:getUserById");
        return userFromDatabase;
    }

    public User createUser(UserCreateRequestDTO userDto) {
        log.debug("IN UserService:createUser");
        User savedUser = userRepository.saveUser(userMapper.mapFromUserCreateRequestDTOToUser(userDto));
        log.debug("OUT UserService:createUser");
        return savedUser;
    }

    public User updateUser(UserUpdateRequestDTO userDto) throws UserUpdateException, UserNotFoundException {
        log.debug("IN UserService:updateUser");
        User userFromDatabase = userRepository.getUserById(userDto.getId());
        if (userFromDatabase == null) {
            throw new UserNotFoundException();
        }

        User result = userRepository.updateUser(userMapper.mapFromUserUpdateRequestDTOToUser(userDto));
        if (result != null) {
            log.info("User with id: {} updated", userDto.getId());
            log.debug("OUT UserService:updateUser");
            return result;
        }
        throw new UserUpdateException("User update failed for userId=" + userDto.getId());
    }


    public void deleteUserById(Integer id) throws UserNotFoundException {
        log.debug("IN UserService:deleteUserById");

        User userFromDatabase = userRepository.getUserById(id);
        if (userFromDatabase == null) {
            throw new UserNotFoundException();
        }
        userRepository.deleteUserById(id);
        log.info("Delete user with id: {}", id);
        log.debug("OUT UserService:deleteUserById");
    }
}
