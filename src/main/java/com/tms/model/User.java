package com.tms.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

@NamedQueries( value = {
        @NamedQuery(name = "getAllUserQuery", query = "FROM User"),
})
@Entity
@Table(name = "users")
@Data
@ToString(exclude = {"security", "products"})
@EqualsAndHashCode(exclude = {"security", "products"})
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

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "user")
    private Security security;

    @ManyToMany(mappedBy = "users", fetch = FetchType.EAGER)
    private List<Product> products;
}