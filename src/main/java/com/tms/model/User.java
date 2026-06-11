package com.tms.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import lombok.Data;

import java.time.LocalDateTime;

@NamedQueries( value = {
        @NamedQuery(name = "getAllUserQuery", query = "FROM User"),
})
@Entity
@Table(name = "users")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Min(value = 18)
    private int age;
    private String email;
    @Column(updatable = false)
    private LocalDateTime created;
    private LocalDateTime updated;
}