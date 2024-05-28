package com.example.apiWithDb.repository;

import com.example.apiWithDb.entities.Company;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CompanyRepository extends JpaRepository<Company,Integer> {
    List<Company> findAllCompaniesByUserId(Long userId);
    Company findByIdAndUserId(Integer id, Long userId);
    Optional<Company> findById(Long id);
    @Transactional
    @Modifying
    String deleteCompanyByIdAndUserId(Long id, Long userId);

    @Query("SELECT c FROM Company c " +
            "JOIN Department d ON c.id = d.company.id " +
            "JOIN Employee e ON d.id = e.department.id " +
            "WHERE e.login = :login")
    Optional<Company> findCompanyByEmployeeLogin(@Param("login") String login);

}
