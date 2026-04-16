package com.tms.model.dto;

import jakarta.validation.constraints.AssertFalse;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Negative;
import jakarta.validation.constraints.NegativeOrZero;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
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

    @Max(100)
    @Min(18)
    private int age;

    @Size(min = 5, max = 50)
    private String username;

    @Email
    private String email;

    @Pattern(regexp = "[A-z]{6,}")
    private String password;

    //@Positive
    //@NotEmpty
    //@NotNull
    //@Null
    //@Negative
    //@NegativeOrZero
    //@PositiveOrZero
    //@Digits(integer = 6, fraction = 2)
    //@DecimalMax("3000.5")
    //@DecimalMin("1000.0")
    //@AssertFalse
    //@AssertTrue
    //@Past
    //@Future
    //@PastOrPresent
    //@FutureOrPresent
}
