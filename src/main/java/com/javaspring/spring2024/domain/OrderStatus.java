package com.javaspring.spring2024.domain;

/**
 * Виды статуса заказа
 */
public enum OrderStatus {
    /**
     * Гитара ожидает арендатора.
     */
    WAITING,
    /**
     * Гитара сдана в аренду
     */
    ACTIVE,
    /**
     * Гитара возвращена, ожидает оценки качества
     */
    FULFILLED,
    /**
     * Гитара возвращена и оценена, заказ закрыт
     */
    COMPLETED
}
