-- liquibase formatted sql

-- changeset popov.d:RAP-3
CREATE TABLE article
(
    id          int8 GENERATED BY DEFAULT AS IDENTITY,
    slug        varchar(255) NOT NULL,
    title       varchar(255) NOT NULL,
    description text         NOT NULL,
    body        text         NOT NULL,
    created_at  timestamp    NOT NULL DEFAULT now(),
    updated_at  timestamp    NOT NULL DEFAULT now(),
    id_author     bigint       NOT NULL,
    CONSTRAINT pk_article_id PRIMARY KEY (id),
    CONSTRAINT uq_article_slug UNIQUE (slug),
    CONSTRAINT fk_user_id FOREIGN KEY (id_author) REFERENCES users (id),
    CONSTRAINT ck_empty_slug CHECK (length(trim(slug)) > 0),
    CONSTRAINT ck_empty_title CHECK (length(trim(title)) > 0),
    CONSTRAINT ck_empty_description CHECK (length(trim(description)) > 0),
    CONSTRAINT ck_empty_body CHECK (length(trim(body)) > 0)
);