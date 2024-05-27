package com.javaspring.spring2024.application.service;

import com.javaspring.spring2024.application.repository.UserRepository;
import com.javaspring.spring2024.domain.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;

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
