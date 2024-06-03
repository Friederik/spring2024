package com.javaspring.spring2024.application.service;

import com.javaspring.spring2024.application.repository.UserRepository;
import com.javaspring.spring2024.domain.Guitar;
import com.javaspring.spring2024.domain.Permission;
import com.javaspring.spring2024.domain.User;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

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
}
