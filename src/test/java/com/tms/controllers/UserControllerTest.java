package com.tms.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tms.exceptions.UserUpdateException;
import com.tms.model.User;
import com.tms.model.dto.UserCreateRequestDTO;
import com.tms.model.dto.UserUpdateRequestDTO;
import com.tms.services.UserService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("Тест для UserController")
@WebMvcTest(controllers = UserController.class, excludeFilters = {
        @ComponentScan.Filter(type = FilterType.REGEX, pattern = "com\\.tms\\.config\\..*")
})
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc; //бин который имитирует запрос в приложение

    @MockBean //имитирует бин для спринга
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    private User userDTO1;
    private User userDTO2;
    private UserCreateRequestDTO userCreateRequestDTO;
    private UserUpdateRequestDTO userUpdateRequestDTO;

    @BeforeEach
    public void setUp() {
        userDTO1 = new User();
        userDTO1.setId(1);
        userDTO1.setFirstName("John");
        userDTO1.setLastName("Smith");
        userDTO1.setAge(20);

        userDTO2 = new User();
        userDTO2.setId(2);
        userDTO2.setFirstName("Jane");
        userDTO2.setLastName("Doe");
        userDTO2.setAge(30);

        userCreateRequestDTO = new UserCreateRequestDTO();
        userCreateRequestDTO.setFirstName("John");
        userCreateRequestDTO.setLastName("Smith");
        userCreateRequestDTO.setAge(20);
        userCreateRequestDTO.setEmail("john.smith@gmail.com");

        userUpdateRequestDTO = new UserUpdateRequestDTO();
        userUpdateRequestDTO.setFirstName("John");
        userUpdateRequestDTO.setLastName("Smith");
        userUpdateRequestDTO.setAge(20);
        userUpdateRequestDTO.setEmail("john.smith@gmail.com");
    }


    @Disabled
    @DisplayName("Поиск пользователя по id - успешный сценарий")
    @Test
    public void getUserById_Success() throws Exception {
        //1. настройка(моки) когда что-то что надо вернуть
        when(userService.getUserById(any())).thenReturn(null);

        //2. запуск метода(mockMvc посылает GET /users/1)
        //3. сравнение результата с эталоном
        mockMvc.perform(get("/users/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.firstName").value("John"))
                .andExpect(jsonPath("$.lastName").value("Smith"))
                .andExpect(jsonPath("$.age").value(20));
    }

    @DisplayName("Поиск пользователя по id - пользователь не найден")
    @Test
    public void getUserById_NotFount() throws Exception {
        //1. настройка(моки) когда что-то что надо вернуть
        when(userService.getUserById(any())).thenReturn(null);

        //2. запуск метода(mockMvc посылает GET /users/1)
        //3. сравнение результата с эталоном
        mockMvc.perform(get("/users/1"))
                .andExpect(status().isNotFound());
    }

    @DisplayName("Поиск всех пользователей - успешный сценарий")
    @Test
    public void getAllUsers_Success() throws Exception {
        List<User> userDTOList = Arrays.asList(userDTO1, userDTO2);
        when(userService.getAllUsers()).thenReturn(userDTOList);

        //2. запуск метода(mockMvc посылает GET /users/1)
        //3. сравнение результата с эталоном
        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(2)))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].firstName").value("John"))
                .andExpect(jsonPath("$[0].lastName").value("Smith"))
                .andExpect(jsonPath("$[0].age").value(20));
    }

    @DisplayName("Создание пользователя - успешный сценарий")
    @Test
    public void createUser_Success() throws Exception {
        userDTO1.setFirstName(userCreateRequestDTO.getFirstName());
        userDTO1.setLastName(userCreateRequestDTO.getLastName());
        userDTO1.setAge(userCreateRequestDTO.getAge());
        userDTO1.setEmail(userCreateRequestDTO.getEmail());

        when(userService.createUser(any(UserCreateRequestDTO.class))).thenReturn(userDTO1);

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userCreateRequestDTO)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.firstName").value(userCreateRequestDTO.getFirstName()))
                .andExpect(jsonPath("$.lastName").value(userCreateRequestDTO.getLastName()))
                .andExpect(jsonPath("$.age").value(userCreateRequestDTO.getAge()))
                .andExpect(jsonPath("$.email").value(userCreateRequestDTO.getEmail()));
    }

    @DisplayName("Создание пользователя - валидация не прошла")
    @Test
    public void createUser_ValidationException() throws Exception {
        userCreateRequestDTO.setEmail("");
        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userCreateRequestDTO)))
                .andExpect(status().isBadRequest());
    }

    @DisplayName("Обновление пользователя - успешный сценарий")
    @Test
    public void updateUser_Success() throws Exception {
        userDTO1.setFirstName(userUpdateRequestDTO.getFirstName());
        userDTO1.setLastName(userUpdateRequestDTO.getLastName());
        userDTO1.setAge(userUpdateRequestDTO.getAge());
        userDTO1.setEmail(userUpdateRequestDTO.getEmail());

        when(userService.updateUser(any(UserUpdateRequestDTO.class))).thenReturn(userDTO1);

        mockMvc.perform(put("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userUpdateRequestDTO)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.firstName").value(userUpdateRequestDTO.getFirstName()))
                .andExpect(jsonPath("$.lastName").value(userUpdateRequestDTO.getLastName()))
                .andExpect(jsonPath("$.age").value(userUpdateRequestDTO.getAge()))
                .andExpect(jsonPath("$.email").value(userUpdateRequestDTO.getEmail()));
    }

    @DisplayName("Обновление пользователя - обработка исключение UserUpdateException")
    @Test
    public void updateUser_UserUpdateException() throws Exception {
        when(userService.updateUser(any(UserUpdateRequestDTO.class))).thenThrow(UserUpdateException.class);
        mockMvc.perform(put("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userUpdateRequestDTO)))
                .andExpect(status().isConflict());
    }

    @Disabled
    @DisplayName("Удаление пользователя по id - успешный сценарий")
    @Test
    public void deleteUserById_Success() throws Exception {
        mockMvc.perform(delete("/users/1"))
                .andExpect(status().isNoContent());
    }

    @Disabled
    @DisplayName("Удаление пользователя по id - ошибка сервера")
    @Test
    public void deleteUserById_NotDeleted() throws Exception {
        mockMvc.perform(delete("/users/1"))
                .andExpect(status().isInternalServerError());
    }
}
