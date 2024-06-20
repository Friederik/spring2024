package com.javaspring.spring2024.application.repository;

import com.javaspring.spring2024.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    Optional<Review> findById(Long id);
    List<Review> findAllByGuitarId(Long id);
    List<Review> findAllByUserId(Long id);
    List<Review> findAllByUserIdAndGuitarIdIsNull(Long id);
}
