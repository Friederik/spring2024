package com.javaspring.spring2024.application.repository;

import com.javaspring.spring2024.domain.Review;
import com.javaspring.spring2024.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findById(Long id);
    void deleteById(Long id);

}
