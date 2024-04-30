-- liquibase formatted sql

-- changeset popov.d:RAP-8

INSERT INTO users (email, username, password, bio)
VALUES ('admin@mail.com', 'admin', '$2a$10$XzgyU/oDRIjlJb57OtsC4.cBQX7N/40zEFUlcLhEjs.3fuy4qH2Mu'/*password*/,
        'admin bio');