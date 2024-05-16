package com.example.apiWithDb.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long  id;

    @Column(nullable = false)
    private String compName;

    @Column(nullable = false)
    private String compAddress;

    @Column(nullable = false)
    private String compLegalAddress;

    @Column(nullable = false)
    private Date compFoundationDate;

    @Column(nullable = true)
    private String compWebsite;

    @Column(nullable = false)
    private String compFieldOfActivity;

    @Column(nullable = false)
    private String compRegistrationNumber;

    @Column(nullable = false)
    private String compVAT;


    @JsonIgnore
    @ManyToOne(targetEntity = User.class, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

}
