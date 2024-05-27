package com.javaspring.spring2024.application.service;

import com.javaspring.spring2024.application.repository.OrderRepository;
import com.javaspring.spring2024.domain.Order;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;

    public Order placeOrder(Order order) {
        if (order == null) throw new IllegalArgumentException("Аргумент order функции createOrder не может быть null");
        try {
            orderRepository.save(order);
            return order;
        } catch (Exception e) {
            throw new RuntimeException("Возникла ошибка при сохранении отзыва в репозиторий.", e);
        }
    }
}
