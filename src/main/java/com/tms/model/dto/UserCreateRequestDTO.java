package com.tms.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "DTO для создание пользователя при регистрации")
@Data
public class UserCreateRequestDTO {
    @Schema(description = "Имя пользователя")
    private String firstName;
    @Schema(description = "Фамилия пользователя")
    private String lastName;
    @Schema(description = "Возраст пользователя")
    private int age;
    @Schema(description = "Email пользователя")
    private String email;
}
