package com.example.apiWithDb.repository;

import com.example.apiWithDb.model.StuffMember;
import org.springframework.data.jpa.repository.JpaRepository;

public interface stuffMemberRepository extends JpaRepository<StuffMember, String> {

}
