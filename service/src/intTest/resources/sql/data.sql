INSERT INTO users (id, email, username, password, bio, image)
VALUES (1, 'test1@mail.com', 'test1', '$2y$10$5yw7PqzBDiFeqAXzUJRd6.4gwgS18kvSnobY5w5B1SuzszQTAigCu', 'bio1', 'image1'),
       (2, 'test2@mail.com', 'test2', '$2y$10$1I4zeN0HRIRUZ6g5gcqhBeEoXjgvBvpfMABQ0cSZAmrmWuuajpi/y', 'bio2', 'image2'),
       (3, 'test3@mail.com', 'test3', '$2y$10$fV1NzHhjGy/6dbGyK/bw2OLNmjqa.O6X9vFoGASdH65PFZfQkJG1u', 'bio3', 'image3'),
       (4, 'test4@mail.com', 'test4', '$2y$10$YqWsXFXg.zMyxkvT4OXqNuKPO7V28kvZOQx/va92rJFWs3q2uglne', 'bio4', 'image4'),
       (5, 'test5@mail.com', 'test5', '$2y$10$OVZ0BhEIVSVsgvFrj3mOB.R/YXIzg10Vu3nZU/dNdsDm4jbr/XVWy', 'bio5', 'image5');
SELECT setval('users_id_seq', (SELECT MAX(id) FROM users));

INSERT INTO follow_relation (id_followee, id_follower)
VALUES (1, 2), (3, 2);

INSERT INTO article (id, slug, title, description, body, id_author)
VALUES (1, 'test-slug-1', 'title1', 'description1', 'body1', 1),
       (2, 'test-slug-2', 'title2', 'description2', 'body2', 1),
       (3, 'test-slug-3', 'title3', 'description3', 'body3', 2),
       (4, 'test-slug-4', 'title4', 'description4', 'body4', 4),
       (5, 'test-slug-5', 'title5', 'description5', 'body5', 5);

INSERT INTO tag (id, tag_name)
VALUES (1, 'tag1'),
       (2, 'tag2'),
       (3, 'tag3');

INSERT INTO article_article_tag (id_tag, id_article)
VALUES (1, 1),
       (2, 1),
       (2, 2),
       (3, 3),
       (1, 4),
       (2, 4),
       (3, 4),
       (1, 5),
       (2, 5);

INSERT INTO article_user_favorite (id_user, id_article)
VALUES (1, 1),
       (1, 2),
       (2, 2),
       (2, 3),
       (2, 4),
       (3, 3),
       (3, 4),
       (5, 1),
       (5, 2);