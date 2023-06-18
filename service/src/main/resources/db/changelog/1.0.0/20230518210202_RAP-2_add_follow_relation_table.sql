-- liquibase formatted sql

-- changeset popov.d:RAP-2
CREATE TABLE follow_relation
(
    id_followee bigint,
    id_follower bigint,
    CONSTRAINT pk_follow_relation_id_followee_id_follower PRIMARY KEY (id_followee, id_follower),
    CONSTRAINT fk_id_followee FOREIGN KEY (id_followee) REFERENCES users (id),
    CONSTRAINT fk_id_follower FOREIGN KEY (id_follower) REFERENCES users (id)
);