package com.modiconme.realworld.it.base.repository;

import jakarta.persistence.*;
import lombok.*;

/**
 * @author popov.d, E-mail popov.d@soft-logic.ru
 * Created on 19.05.2024
 */
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
@ToString
@Getter
@Entity
@Table(name = "tag")
public class TagEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String tagName;
}