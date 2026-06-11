package com.tms.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Schema(description = "DTO для создание пользователя при регистрации")
@Data
public class UserCreateRequestDTO {
    @NotBlank
    @Schema(description = "Имя пользователя")
    private String firstName;
    @NotBlank
    @Schema(description = "Фамилия пользователя")
    @NotBlank
    private String lastName;
    @Schema(description = "Возраст пользователя")
    @Min(value = 18)
    private int age;
    @Email
    @NotBlank
    @Schema(description = "Email пользователя")
    private String email;
}
