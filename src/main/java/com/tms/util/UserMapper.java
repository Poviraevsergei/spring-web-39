package com.tms.util;

import com.tms.model.User;
import com.tms.model.dto.RegistrationRequestDTO;
import com.tms.model.dto.UserCreateRequestDTO;
import com.tms.model.dto.UserUpdateRequestDTO;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class UserMapper {

    public User mapFromUserUpdateRequestDTOToUser(UserUpdateRequestDTO userDTO) {
        if (userDTO == null) {
            throw new NullPointerException();
        }
        User user = new User();
        user.setId(userDTO.getId());
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setAge(userDTO.getAge());
        user.setEmail(userDTO.getEmail());
        user.setUpdated(LocalDateTime.now());
        return user;
    }

    public User mapFromUserCreateRequestDTOToUser(UserCreateRequestDTO userDTO) {
        if (userDTO == null) {
            throw new NullPointerException();
        }
        User user = new User();
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setAge(userDTO.getAge());
        user.setEmail(userDTO.getEmail());
        user.setCreated(LocalDateTime.now());
        user.setUpdated(LocalDateTime.now());
        return user;
    }

    public User mapFromRegistrationRequestDTOToUser(RegistrationRequestDTO userDTO) {
        if (userDTO == null) {
            throw new NullPointerException();
        }
        User user = new User();
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setAge(userDTO.getAge());
        user.setEmail(userDTO.getEmail());
        user.setCreated(LocalDateTime.now());
        user.setUpdated(LocalDateTime.now());
        return user;
    }
}
