package com.modiconme.realworld.domain.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "tag")
public class TagEntity implements Comparable<TagEntity> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String tagName;

    public TagEntity(String tagName) {
        this.tagName = tagName;
    }

    @Override
    public int compareTo(TagEntity t) {
        return this.tagName.compareTo(t.tagName);
    }
}
