package com.javaspring.spring2024.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

/**
 * Сущность гитары.
 */
@Entity
@Table(name = "guitars")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Guitar {

    /**
     * Уникальный идентификатор гитары.
     */
    private String guitarId;

    /**
     * Название гитары.
     */
    private String name;

    /**
     * Тип гитары.
     */
    private String type;

    /**
     * Производитель гитары.
     */
    private String manufacturer;

    /**
     * Дерево из которого сделан гриф гитары.
     */
    private String woodBoard;

    /**
     * Дерево из которого сделана дека гитары.
     */
    private String woodDeck;

    /**
     * Рейтинг гитары.
     */
    private float rating;

    /**
     * Рейтинг гитары.
     */
    private Condition condition;

    /**
     * Находиться ли гитара  в заказе.
     */
    private String orderId;
}
