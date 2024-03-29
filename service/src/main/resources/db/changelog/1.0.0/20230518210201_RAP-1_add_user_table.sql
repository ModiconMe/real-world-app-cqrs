-- liquibase formatted sql

-- changeset popov.d:RAP-1
CREATE TABLE users
(
    id         int8 GENERATED BY DEFAULT AS IDENTITY,
    email      varchar(128) NOT NULL,
    username   varchar(128) NOT NULL,
    password   varchar(256) NOT NULL,
    bio        text,
    image      text,
    created_at timestamp NOT NULL DEFAULT now(),
    updated_at timestamp NOT NULL DEFAULT now(),
    CONSTRAINT pk_user_id PRIMARY KEY (id),
    CONSTRAINT uq_user_email UNIQUE (email),
    CONSTRAINT ck_empty_email CHECK (length(trim(email)) > 0),
    CONSTRAINT ck_empty_username CHECK (length(trim(username)) > 0),
    CONSTRAINT ck_empty_password CHECK (length(trim(password)) > 0),
    CONSTRAINT ck_empty_bio CHECK (length(trim(bio)) > 0 OR NULL),
    CONSTRAINT ck_empty_image CHECK (length(trim(image)) > 0 OR NULL)
);