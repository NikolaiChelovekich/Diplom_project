package com.example.apiWithDb.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
public class AttendanceRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long  id;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(nullable = false)
    private LocalDate recordDate;

    @Column(nullable = false)
    private LocalTime dailyTimeWorked;

    //@Column(nullable = false)
    // private LocalTime WorkedTimeInMonth;

    @ManyToOne(targetEntity = Employee.class, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "employee_id", referencedColumnName = "id")
    private Employee employee;

}