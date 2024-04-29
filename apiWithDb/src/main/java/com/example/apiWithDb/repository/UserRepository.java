package com.example.apiWithDb.repository;

import com.example.apiWithDb.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    public Optional<User> findBylogin(String login);
}
