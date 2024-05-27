package com.javaspring.spring2024.domain;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;


/**
 * Сущность оценки.
 */
@Entity
@Table(name = "reviews")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Review {

    /**
     * Номер оценки.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private Long reviewId;

    /**
     * Номер гитары, на которую делается оценка. Если пустая, то оценка на пользователя.
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "guitar_id")
    @Nullable
    private Guitar guitar;

    /**
     * Номер пользователя.
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    /**
     * Дата оценки.
     */
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @Column(name = "review_date")
    private LocalDateTime dateOfOrder;

    /**
     * Значение оценки.
     */
    @Setter
    @Column(name = "rating")
    private Short rating;
}
