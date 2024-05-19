package com.modiconme.realworld.it.base.repository;

import jakarta.persistence.*;
import lombok.*;

/**
 * @author popov.d, E-mail popov.d@soft-logic.ru
 * Created on 19.05.2024
 */
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@Entity
@Table(name = "article_article_tag")
public class ArticleTagEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_article", referencedColumnName = "id", nullable = false)
    private ArticleEntity article;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_tag", referencedColumnName = "id", nullable = false)
    private TagEntity tag;

}
