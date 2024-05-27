package com.javaspring.spring2024.application.service;

import com.javaspring.spring2024.application.repository.ReviewRepository;
import com.javaspring.spring2024.domain.Review;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;

    public Review createReview(Review review) {
        if (review == null) throw new IllegalArgumentException("Аргумент review функции createReview не может быть null");
        try {
            reviewRepository.save(review);
            return review;
        } catch (Exception e) {
            throw new RuntimeException("Возникла ошибка при сохранении отзыва в репозиторий.", e);
        }
    }
}
