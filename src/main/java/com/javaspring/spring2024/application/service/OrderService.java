package com.javaspring.spring2024.application.service;

import com.javaspring.spring2024.application.repository.OrderRepository;
import com.javaspring.spring2024.domain.Order;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;

    /**
     * Создает новый заказ в базу данных.
     *
     * @param order Сущность заказа для создания.
     * @return Сущность созданного заказа.
     */
    public Order createOrder(Order order) {
        if (order == null) throw new IllegalArgumentException("Аргумент order функции createOrder не может быть null");
        try {
            orderRepository.save(order);
            return order;
        } catch (Exception e) {
            throw new RuntimeException("Возникла ошибка при сохранении отзыва в репозиторий.", e);
        }
    }
}
