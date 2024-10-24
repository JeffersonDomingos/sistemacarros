package com.aps.sistemacarros.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aps.sistemacarros.model.User;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.aps.sistemacarros.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}