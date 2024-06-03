package com.javaspring.spring2024.application.service;

import com.javaspring.spring2024.application.repository.GuitarRepository;
import com.javaspring.spring2024.application.repository.OrderRepository;
import com.javaspring.spring2024.application.repository.ReviewRepository;
import com.javaspring.spring2024.domain.Guitar;
import com.javaspring.spring2024.domain.Order;
import com.javaspring.spring2024.domain.OrderStatus;
import com.javaspring.spring2024.domain.Review;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class GuitarService {
    private final GuitarRepository guitarRepository;
    private final OrderRepository orderRepository;
    private final ReviewRepository reviewRepository;

    /**
     * Создает новую гитару в базе данных.
     *
     * @param guitar Сущность гитары для создания.
     * @return Сущность созданной гитары.
     */
    public Guitar createGuitar(Guitar guitar) {
        if (guitar == null) throw new IllegalArgumentException("Аргумент guitar функции addGuitar не может быть null");
        try {
            guitarRepository.save(guitar);
            return guitar;
        } catch (Exception e) {
            throw new RuntimeException("Возникла ошибка при сохранении гитары в репозиторий.", e);
        }
    }

    /**
     * Удаляет гитару из базы данных, используя ее сущность.
     *
     * @param guitar Сущность гитары, которую нужно удалить.
     */
    public void deleteGuitar(Guitar guitar) {
        if(guitar == null) throw new IllegalArgumentException("Аргумент guitar функции deleteGuitar не может быть null");
        deleteGuitar(guitar.getId());
    }

    /**
     * Удаляет гитару из базы данных, используя ее идентификатор.
     *
     * @param guitarId Идентификатор гитары, которую нужно удалить.
     */
    public void deleteGuitar(long guitarId) {
        Optional<Guitar> guitar = guitarRepository.findById(guitarId);
        if (guitar.isEmpty()) {
            throw new IllegalArgumentException("Гитары с id " + guitarId + " не существует в базе.");
        } else {
            guitarRepository.deleteById(guitarId);
        }
    }

    /**
     * Обновляет рейтинг гитары, используя оставленные отзывы клиентов. Используется сущность гитары.
     *
     * @param guitar Сущность гитары, для которой нужно обновить рейтинг.
     */
    public void updateGuitarRating(Guitar guitar) {
        if(guitar == null) throw new IllegalArgumentException("Аргумент guitar функции updateGuitarRating не может быть null");
        updateGuitarRating(guitar.getId());
    }

    /**
     * Обновляет рейтинг гитары, используя оставленные отзывы клиентов. Используется идентификатор гитары.
     *
     * @param guitarId Идентификатор гитары, для которой нужно обновить рейтинг.
     */
    public void updateGuitarRating(long guitarId) {
        Optional<Guitar> guitar = guitarRepository.findById(guitarId);
        if(guitar.isEmpty()) {
            throw new IllegalArgumentException("Гитары с id " + guitarId + " не существует в базе.");
        } else {
            List<Review> reviews = reviewRepository.findAllByGuitarId(guitarId);
            if(reviews.isEmpty()) {
                guitar.get().setRating(5.0f);
            } else {
                float fullRating = 0;
                for(Review review : reviews) {
                    fullRating += review.getRating();
                }
                guitar.get().setRating(fullRating / reviews.size());
            }
        }
    }

    /**
     * Возвращает список доступных к аренде гитар.
     *
     * @return Гитары, которые возможно взять в аренду.
     */
    public List<Guitar> getAvailableGuitars() {
        List<Guitar> guitars = guitarRepository.findAll();
        List<Guitar> availableGuitars = new java.util.ArrayList<>(List.of());
        guitars.forEach(guitar -> {
            List<Order> ordersNotCompleted = orderRepository.findByGuitarIdAndOrderStatusNotOrderByIdDesc(guitar.getId(), OrderStatus.COMPLETED);
            if(ordersNotCompleted.isEmpty()) {
                guitar.setOrderStatus(OrderStatus.COMPLETED);
                availableGuitars.add(guitar);
            } else {
                guitar.setOrderStatus(ordersNotCompleted.get(0).getOrderStatus());
            }
        });
        return availableGuitars;
    }
}
