package com.example.apiWithDb.entities;

import com.example.apiWithDb.utils.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

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
    private String PhoneNumber;

    @Column(nullable = false)
    private String Position;

    @Column(nullable = false)
    private Date BirthDate;

    @Column(nullable = false)
    private String Country;

    @Enumerated(EnumType.STRING)
    @Column(nullable = true)
    private Role role;

    @Column(nullable = false)
    private String login;

    @Column(nullable = false)
    private String password;

    @JsonIgnore
    @ManyToOne(targetEntity = Department.class)
    @JoinColumn(name = "department_id", referencedColumnName = "id")
    private Department department;

    @JsonIgnore
    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AttendanceRecord> attendanceRecords;
   // @JsonIgnore
//    public Role getRole() {
//        return role;
//    }

}