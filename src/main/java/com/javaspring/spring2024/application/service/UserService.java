package com.javaspring.spring2024.application.service;

import com.javaspring.spring2024.application.repository.OrderRepository;
import com.javaspring.spring2024.application.repository.ReviewRepository;
import com.javaspring.spring2024.application.repository.UserRepository;
import com.javaspring.spring2024.domain.*;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.ast.Or;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final ReviewRepository reviewRepository;
    private final OrderRepository orderRepository;

    /**
     * Добавляет нового пользователя в базу данных.
     *
     * @param user Сущность пользователя, добавляемого в базу данных.
     * @return Сущность созданного пользователя.
     */
    public User createUser(User user) {
        if (user == null) throw new IllegalArgumentException("Аргумент user функции createUser не может быть null");
        try {
            userRepository.save(user);
            return user;
        } catch (Exception e) {
            throw new RuntimeException("Возникла ошибка при добавлении пользователя в репозиторий.", e);
        }
    }

    /**
     * Удаляет пользователя из базы данных, используя его сущность.
     *
     * @param user Сущность пользователя, которого нужно удалить.
     */
    public void deleteUser(User user) {
        if (user == null) throw new IllegalArgumentException("Аргумент user функции deleteUser не может быть null");
        deleteUser(user.getId());
    }

    /**
     * Удаляет пользователя из базы данных, используя его идентификатор.
     *
     * @param userId Идентификатор пользователя, которого нужно удалить.
     */
    public void deleteUser(long userId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            throw new IllegalArgumentException("Пользователя с id " + userId + " не существует в базе.");
        } else {
            userRepository.deleteById(userId);
        }
    }

    /**
     * Обновляет рейтинг пользователя, используя оставленные отзывы. Используется сущность пользователя.
     *
     * @param user Сущность пользователя, для которого нужно обновить рейтинг.
     */
    public void updateUserRating(User user) {
        if(user == null) throw new IllegalArgumentException("Аргумент user функции updateUserRating не может быть null");
        updateUserRating(user.getId());
    }

    /**
     * Обновляет рейтинг пользователя, используя оставленные отзывы. Используется идентификатор пользователя.
     *
     * @param userId Идентификатор пользователя, для которого нужно обновить рейтинг.
     */
    public void updateUserRating(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        if(user.isEmpty()) {
            throw new IllegalArgumentException("Пользователя с id " + userId + " не существует");
        } else {
            List<Review> reviews = reviewRepository.findAllByUserIdAndGuitarIdIsNull(userId);
            if(reviews.isEmpty()) {
                user.get().setRating(5.0f);
            } else {
                float fullRating = 0;
                for(Review review : reviews) {
                    fullRating += review.getRating();
                }
                user.get().setRating(fullRating / reviews.size());
            }
        }
    }
}
