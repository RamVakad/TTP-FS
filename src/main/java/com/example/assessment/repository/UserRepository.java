package com.example.assessment.repository;

import com.example.assessment.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;

public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByEmail(String email);

    User findByEmail(String email);

    @Transactional
    void deleteByEmail(String email);

}