package com.example.apiWithDb.entities;

import com.example.apiWithDb.utils.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Table(name = "app_user")
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Column(name = "first_name",nullable = false)
    private String firstName;

    @Column(name = "lastName", nullable = false)
    private String lastName;

    @Column(nullable = false)
    private String login;

    @Column(nullable = false)
    private String country;

    @JsonIgnore
    @Enumerated(EnumType.STRING)
    @Column(nullable = true)
    private Role role;

    @Column(nullable = false)
    private String password;

    @JsonIgnore
    @OneToOne(mappedBy = "user")
    private ForgotPassword forgotPassword;



    public User(String login) {
        this.login = login;
    }
}
