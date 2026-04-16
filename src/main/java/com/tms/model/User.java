package com.tms.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class User {
    private int id;
    private String firstName;
    private String lastName;
    private int age;
    private String email;
    private LocalDateTime created;
    private LocalDateTime updated;
}
