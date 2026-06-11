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
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserMapper userMapper;
    private final UserRepository userRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(Integer id) {
        log.debug("IN UserService:getUserById");
        Optional<User> userFromDatabase = userRepository.findById(id);
        log.debug("OUT UserService:getUserById");
        return userFromDatabase;
    }

    public Optional<User> getInfoAboutMyself() {
        log.debug("IN UserService:getInfoAboutMyself");
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<User> user = userRepository.findByUsername(username);
        log.debug("OUT UserService:getInfoAboutMyself");
        return user;
    }

    public User createUser(UserCreateRequestDTO userDto) {
        log.debug("IN UserService:createUser");
        User savedUser = userRepository.save(userMapper.mapFromUserCreateRequestDTOToUser(userDto));
        log.debug("OUT UserService:createUser");
        return savedUser;
    }

    public User updateUser(UserUpdateRequestDTO userDto) throws UserUpdateException, UserNotFoundException {
        log.debug("IN UserService:updateUser");
        Optional<User> userFromDatabase = userRepository.findById(userDto.getId());
        if (userFromDatabase.isEmpty()) {
            throw new UserNotFoundException();
        }

        User result = userRepository.save(userMapper.mapFromUserUpdateRequestDTOToUser(userDto));
        log.info("User with id: {} updated", userDto.getId());
        log.debug("OUT UserService:updateUser");
        return result;
    }

    public void deleteUserById(Integer id) throws UserNotFoundException {
        log.debug("IN UserService:deleteUserById");

        Optional<User> userFromDatabase = userRepository.findById(id);
        if (userFromDatabase.isEmpty()) {
            throw new UserNotFoundException();
        }
        userRepository.deleteById(id);
        log.info("Delete user with id: {}", id);
        log.debug("OUT UserService:deleteUserById");
    }
}
