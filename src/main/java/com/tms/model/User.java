package com.tms.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Data
public class User {
    @Id
    @SequenceGenerator(name = "user_seq_generator", sequenceName = "user_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "user_seq_generator")
    private Integer id;

    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    private int age;
    private String email;
    private LocalDateTime created;
    private LocalDateTime updated;
}
