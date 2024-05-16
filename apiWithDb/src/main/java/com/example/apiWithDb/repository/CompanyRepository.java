package com.example.apiWithDb.repository;

import com.example.apiWithDb.entities.Company;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import java.util.List;
import java.util.Optional;

public interface CompanyRepository extends JpaRepository<Company,Integer> {
    List<Company> findAllCompaniesByUserId(Long userId);
    Company findByIdAndUserId(Integer id, Long userId);

    @Transactional
    @Modifying
    String deleteCompanyByIdAndUserId(Integer id, Long userId);

}
