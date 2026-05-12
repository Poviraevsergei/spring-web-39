package com.tms.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class UserUpdateRequestDTO {
    @Positive
    private Integer id;
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    @Min(18)
    private int age;
    @NotBlank
    @Email
    private String email;
}
