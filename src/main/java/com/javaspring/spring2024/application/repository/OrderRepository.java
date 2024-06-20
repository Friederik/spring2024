package com.javaspring.spring2024.application.repository;

import com.javaspring.spring2024.domain.Order;
import com.javaspring.spring2024.domain.OrderStatus;
import org.aspectj.weaver.ast.Or;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    Optional<Order> findById(Long id);
    List<Order> findByOrderStatus(OrderStatus status);
    List<Order> findByGuitarIdAndOrderStatusNotOrderByIdDesc(long guitarId, OrderStatus status);
    List<Order> findByUserIdAndOrderStatusNotOrderByIdDesc(long userId, OrderStatus status);
    Order findTopByGuitarIdOrderByIdDesc(Long guitarId);
}
