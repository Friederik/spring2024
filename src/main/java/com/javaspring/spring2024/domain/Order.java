package com.javaspring.spring2024.domain;


import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

/**
 * Сущность заказа.
 */
@Entity
@Table(name = "orders")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Order {

    /**
     * Номер заказа.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;

    /**
     * Номер гитары.
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "guitar_id")
    private Guitar guitar;

    /**
     * Номер пользователя.
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    /**
     * Дата заказа.
     */
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @Column(name = "order_date")
    private LocalDateTime dateOfOrder;

    /**
     * Статус заказа.
     */
    @Setter
    @Enumerated(EnumType.STRING)
    @Column(name = "order_status")
    private OrderStatus orderStatus;
}
