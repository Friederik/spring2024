package com.javaspring.spring2024.application.service;

import com.javaspring.spring2024.application.repository.GuitarRepository;
import com.javaspring.spring2024.domain.Guitar;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GuitarService {
    private final GuitarRepository guitarRepository;

    public Guitar addGuitar(Guitar guitar) {
        if (guitar == null) throw new IllegalArgumentException("Аргумент guitar функции addGuitar не может быть null");
        try {
            guitarRepository.save(guitar);
            return guitar;
        } catch (Exception e) {
            throw new RuntimeException("Возникла ошибка при сохранении гитары в репозиторий.", e);
        }
    }
}
