package com.javaspring.spring2024.domain;

import java.time.LocalDateTime;


/**
 * Сущность оценки.
 */
public class Review {

    /**
     * Номер оценки.
     */
    private String reviewId;

    /**
     * Номер гитары, на которую делается оценка. Если пустая, то оценка на пользователя.
     */
    private String guitarId;

    /**
     * Номер пользователя.
     */
    private String userId;

    /**
     * Дата оценки.
     */
    private LocalDateTime dateOfOrder;

    /**
     * Значение оценки.
     */
    private short rating;
}
