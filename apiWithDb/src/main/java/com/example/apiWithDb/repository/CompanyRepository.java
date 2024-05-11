package com.example.apiWithDb.repository;

import com.example.apiWithDb.entities.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company,Integer> {
}
