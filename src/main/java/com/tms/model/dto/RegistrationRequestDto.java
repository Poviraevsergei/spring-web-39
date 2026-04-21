package com.tms.model.dto;

import com.tms.model.annotations.SixSeven;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegistrationRequestDto {
    @NotBlank
    @Size(min = 2, max = 50)
    private String firstName;

    @NotBlank
    @Size(min = 2, max = 50)
    private String lastName;

    @SixSeven
    private int age;

    @Size(min = 5, max = 50)
    private String username;

    @Email
    private String email;

    @Pattern(regexp = "[A-z]{6,}")
    private String password;
}
