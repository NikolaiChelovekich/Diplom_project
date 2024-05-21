package com.example.apiWithDb.entities;

import com.example.apiWithDb.utils.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="employees")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long  id;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    private String employeePhoto;

    @Column(nullable = false)
    private String Country;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Column(nullable = false)
    private String login;

    @Column(nullable = false)
    private String password;

    @JsonIgnore
    @ManyToOne(targetEntity = Department.class, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "department_id", referencedColumnName = "id")
    private Department department;

}