package com.javaspring.spring2024.domain;

import jakarta.persistence.*;
import lombok.*;

/**
 * Сущность гитары.
 */
@Entity
@Table(name = "guitars")
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class Guitar {

    /**
     * Уникальный идентификатор гитары.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "guitar_id")
    private Long id;

    /**
     * Название гитары.
     */
    @Column(name = "guitar_name")
    private String name;

    /**
     * Тип гитары.
     */
    @Setter
    @Column(name = "guitar_type")
    private String type;

    /**
     * Производитель гитары.
     */
    @Setter
    @Column(name = "guitar_manufacturer")
    private String manufacturer;

    /**
     * Дерево из которого сделан гриф гитары.
     */
    @Setter
    @Column(name = "board_wood")
    private String boardWood;

    /**
     * Дерево из которого сделана дека гитары.
     */
    @Setter
    @Column(name = "deck_wood")
    private String deckWood;

    /**
     * Рейтинг гитары.
     */
    @Setter
    @Column(name = "guitar_rating")
    private float rating;

    /**
     * Состояние гитары.
     */
    @Setter
    @Enumerated(EnumType.STRING)
    @Column(name = "guitar_condition")
    private Condition condition;

    @Setter
    @Enumerated(EnumType.STRING)
    @Column(name = "guitar_order_status")
    private OrderStatus orderStatus;
}
