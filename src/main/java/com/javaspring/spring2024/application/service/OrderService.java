package com.javaspring.spring2024.application.service;

import com.javaspring.spring2024.application.repository.GuitarRepository;
import com.javaspring.spring2024.application.repository.OrderRepository;
import com.javaspring.spring2024.application.repository.ReviewRepository;
import com.javaspring.spring2024.application.repository.UserRepository;
import com.javaspring.spring2024.domain.*;
import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.ast.Or;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final GuitarRepository guitarRepository;
    private final UserRepository userRepository;
    private final ReviewRepository reviewRepository;

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
        else if(user.get().getRating() >= guitar.get().getRating()){
            Order order = Order.builder()
                    .user(user.get())
                    .guitar(guitar.get())
                    .orderStatus(OrderStatus.WAITING)
                    .dateOfOrder(dateTime.atStartOfDay())
                    .build();
            orderRepository.save(order);
        }
        else {
            throw new IllegalArgumentException("Рейтинг пользователя слишком мал для этой гитары.");
        }
    }

    /**
     * Выдает гитару, используя идентификатор заказа.
     *
     * @param orderId Идентификатор заказа.
     */
    public void takingGuitar(Long orderId) {
        Optional<Order> order = orderRepository.findById(orderId);
        if(order.isEmpty()) {
            throw new IllegalArgumentException("Заказ с id " + orderId + " не найден.");
        }
        else if(order.get().getOrderStatus() == OrderStatus.WAITING){
            order.get().setOrderStatus(OrderStatus.ACTIVE);
        }
        else {
            throw new IllegalArgumentException("Данный заказа не готов к выдаче.");
        }
    }

    /**
     * Возвращает гитару и перенаправляет ее на проверку, используя идентификатор заказа.
     *
     * @param orderId Идентификатор заказа.
     */
    public void returnToRatingGuitar(Long orderId) {
        Optional<Order> order = orderRepository.findById(orderId);
        if(order.isEmpty()) {
            throw new IllegalArgumentException("Заказ с id " + orderId + " не найден.");
        }
        else if(order.get().getOrderStatus() == OrderStatus.ACTIVE) {
            order.get().setOrderStatus(OrderStatus.FULFILLED);
        }
        else {
            throw new IllegalArgumentException("Гитара не находится в аренде.");
        }
    }

    /**
     * Возвращает гитару с оценки в магазин, используя идентификатор заказа.
     *
     * @param orderId Идентификатор заказа.
     */
    public void returnToShopGuitar(Long orderId, int guitarRating, int userRating) {
        Optional<Order> order = orderRepository.findById(orderId);
        if(order.isEmpty()) {
            throw new IllegalArgumentException("Заказ с id " + orderId + " не найден.");
        }
        if(guitarRating < 1 || guitarRating > 10) {
            throw new IllegalArgumentException("Некоректные значения рейтинга гитары.");
        }
        if(userRating < 1 || userRating > 10) {
            throw new IllegalArgumentException("Некоректные значения рейтинга пользователя.");
        }
        Guitar guitar = order.get().getGuitar();
        User user = order.get().getUser();
        if(order.get().getOrderStatus() == OrderStatus.FULFILLED) {
            Review guitarReview = Review.builder()
                    .guitar(guitar)
                    .user(user)
                    .dateOfOrder(LocalDateTime.now())
                    .rating(guitarRating)
                    .build();
            int totalRating = calculateRating(order.get(), userRating);
            Review userReview = Review.builder()
                    .guitar(null)
                    .user(user)
                    .dateOfOrder(LocalDateTime.now())
                    .rating(totalRating)
                    .build();
            reviewRepository.save(guitarReview);
            reviewRepository.save(userReview);
            order.get().setOrderStatus(OrderStatus.COMPLETED);
        }
        else {
            throw new IllegalArgumentException("Гитара не находится на оценке.");
        }
    }

    /**
     * Высчитывает рейтинг, используя рейтинг, поставленный пользователю, и дату сдачи гитары.
     *
     * @param order Заказ, который проходит диагностику.
     * @param userRating Рейтинг, поставленный пользователю.
     * @return Итоговый рейтинг пользователя.
     */
    public int calculateRating(Order order, int userRating) {
        LocalDateTime time = LocalDateTime.now();
        LocalDateTime returnTime = order.getDateOfOrder();
        int daysFromReturn = Period.between(returnTime.toLocalDate(), time.toLocalDate()).getDays();
        if(daysFromReturn > 5) {
            daysFromReturn = 5;
        } else if(daysFromReturn < -5) {
            daysFromReturn = -5;
        }
        userRating = userRating - daysFromReturn;
        if(userRating > 10) {
            userRating = 10;
        } else  if(userRating < 0) {
            userRating = 0;
        }
        return userRating;
    }
}
