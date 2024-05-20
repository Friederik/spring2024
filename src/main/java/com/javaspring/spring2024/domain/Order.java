package com.javaspring.spring2024.domain;


import java.time.LocalDateTime;

/**
 * Сущность заказа.
 */
public class Order {

    /**
     * Номер заказа.
     */
    private String orderId;

    /**
     * Номер гитары.
     */
    private String guitarId;

    /**
     * Номер пользователя.
     */
    private String userId;

    /**
     * Дата заказа.
     */
    private LocalDateTime dateOfOrder;
}
