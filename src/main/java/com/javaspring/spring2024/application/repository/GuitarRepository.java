package com.javaspring.spring2024.application.repository;

import com.javaspring.spring2024.domain.Guitar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GuitarRepository extends JpaRepository<Guitar, Long> {
    Optional<Guitar> findById(Long id);
    Optional<Guitar> findByName(String name);
}
