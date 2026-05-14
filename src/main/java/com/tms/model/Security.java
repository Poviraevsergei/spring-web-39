package com.tms.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "security")
@Data
public class Security {
    @Id
    @SequenceGenerator(name = "security_seq_generator", sequenceName = "security_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "security_seq_generator")
    private Integer id;
    private String username;
    private String password;

    @Column(unique = true, name = "user_id")
    private int userId;
    private LocalDateTime created;
    private LocalDateTime updated;

    @Enumerated(EnumType.STRING)
    private Role role;
}
