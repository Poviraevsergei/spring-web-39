package com.tms.model.dto;

import lombok.Data;

@Data
public class UserUpdateRequestDTO {
    private Integer id;
    private String firstName;
    private String lastName;
    private int age;
    private String email;
}
