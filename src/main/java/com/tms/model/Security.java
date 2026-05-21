package com.tms.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.LocalDateTime;

@Entity
@Table(name = "security")
@Data
@ToString(exclude = {"user"})
@EqualsAndHashCode(exclude = {"user"})
public class Security {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String username;

    @JsonIgnore
    private String password;
    private LocalDateTime created;
    private LocalDateTime updated;

    @Enumerated(EnumType.STRING)
    private Role role;

    @JsonBackReference
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
}
