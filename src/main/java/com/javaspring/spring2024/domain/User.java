package com.javaspring.spring2024.domain;


import jakarta.persistence.*;
import lombok.*;

/**
 * Сущность пользователя.
 */
@Entity
@Table(name = "users")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {

    /**
     * Номер пользователя.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    /**
     * Фамилия пользователя.
     */
    @Setter
    @Column(name = "user_lastname")
    private String lastName;

    /**
     * Имя пользователя.
     */
    @Setter
    @Column(name = "user_firstname")
    private String firstName;

    //TODO авторизация
    /*
      Пароль пользователя.

    @
    private String password;
     */

    /**
     * Рейтинг пользователя.
     */
    @Setter
    @Column(name = "user_rating")
    private Float rating;

    /**
     * Уровень прав пользователя.
     */
    @Setter
    @Enumerated(EnumType.STRING)
    @Column(name = "user_permission_level")
    private Permission permissionLevel;
}
