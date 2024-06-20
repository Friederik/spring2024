package com.javaspring.spring2024.application.service;

import com.javaspring.spring2024.application.repository.GuitarRepository;
import com.javaspring.spring2024.application.repository.OrderRepository;
import com.javaspring.spring2024.application.repository.UserRepository;
import com.javaspring.spring2024.domain.Guitar;
import com.javaspring.spring2024.domain.Order;
import com.javaspring.spring2024.domain.OrderStatus;
import com.javaspring.spring2024.domain.User;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final GuitarRepository guitarRepository;
    private final UserRepository userRepository;

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

    /**
     * Возвращает список доступных к аренде гитар.
     *
     * @return Гитары, которые возможно взять в аренду.
     */
    public List<Guitar> getAvailableGuitars() {
        List<Guitar> guitars = guitarRepository.findAll();
        List<Guitar> availableGuitars = new java.util.ArrayList<>(List.of());
        guitars.forEach(guitar -> {
            if(checkGuitarAvailability(guitar.getId())) {
                guitar.setOrderStatus(OrderStatus.COMPLETED);
                availableGuitars.add(guitar);
            } else {
                Order lastOrder = orderRepository.findTopByGuitarIdOrderByIdDesc(guitar.getId());
                guitar.setOrderStatus(lastOrder.getOrderStatus());
            }
        });
        return availableGuitars;
    }

    /**
     * Проверяет доступна ли гитара к аренде.
     *
     * @param guitarId Идентификатор гитары, которая проверяется на доступность.
     * @return Доступна ли гитара.
     */
    public boolean checkGuitarAvailability(Long guitarId) {
        Optional<Guitar> guitar = guitarRepository.findById(guitarId);
        if(guitar.isEmpty()) {
            throw new IllegalArgumentException("Гитара с id " + guitarId + " не найдена.");
        }
        List<Order> ordersNotCompleted = orderRepository.findByGuitarIdAndOrderStatusNotOrderByIdDesc(guitarId, OrderStatus.COMPLETED);
        return ordersNotCompleted.isEmpty();
    }

    /**
     * Находит количество заказов у пользователя, используя идентификатор пользователя.
     *
     * @param userId Идентификатор пользователя, который проверяется.
     * @return Количество заказов у заданного пользователя.
     */
    public int checkUserAvailability(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        if(user.isEmpty()) {
            throw new IllegalArgumentException("Пользователя с id " + userId + " не найден");
        }
        List<Order> ordersOfUser = orderRepository.findByUserIdAndOrderStatusNotOrderByIdDesc(userId, OrderStatus.COMPLETED);
        return ordersOfUser.size();
    }

    /**
     * Заказывает гитару, используя идентификатор пользователя, гитары и даты заказа.
     *
     * @param userId Идентификатор пользователя, который заказывает гитару.
     * @param guitarId Идентификатор гитары, которую берут в аренду.
     * @param dateTime Время, когда нужно забрать гитару.
     */
    public void orderGuitar(Long userId, Long guitarId, LocalDate dateTime) {
        Optional<Guitar> guitar = guitarRepository.findById(guitarId);
        Optional<User> user = userRepository.findById(userId);
        if(guitar.isEmpty()) {
            throw new IllegalArgumentException("Гитара с id " + guitarId + " не найдена.");
        }
        if(user.isEmpty()) {
            throw new IllegalArgumentException("Пользователя с id " + userId + " не найден.");
        }
        if(!checkGuitarAvailability(guitarId)) {
            throw new IllegalArgumentException("Гитара с id " + guitarId + " недоступна для заказа.");
        } else if(checkUserAvailability(userId) >= 2) {
            throw new IllegalArgumentException("У пользователя с id " + guitarId + " нет возможности взять заказ.");
        }
        else {

            Order order = Order.builder()
                    .user(user.get())
                    .guitar(guitar.get())
                    .orderStatus(OrderStatus.WAITING)
                    .dateOfOrder(dateTime.atStartOfDay())
                    .build();
            orderRepository.save(order);
        }
    }
}
