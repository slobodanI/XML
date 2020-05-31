-- Lozinke su hesovane pomocu BCrypt algoritma https://www.dailycred.com/article/bcrypt-calculator
-- Lozinka za oba user-a je 1234567899

INSERT INTO USERS (username, password, first_name, last_name, email, accepted, blocked, canceled, owes, last_password_reset_date, salt)
	VALUES ('user', '$2a$10$Fbq6q3mR.j4bgoAIKTJbYOr6ccjsMNZdmlKwRikJP8gLkx60jaSmG', 'Marko', 'Markovic', 'user@example.com', true, false, 0, 0, '2017-10-01 21:58:58.508-07', 'a0s8hgy28h1');
INSERT INTO USERS (username, password, first_name, last_name, email, accepted, blocked, canceled, owes, last_password_reset_date, salt)
	VALUES ('admin', '$2a$10$eH8zX6i0FSIWRj4h9fSMdOJFCrMLuoI8aJIp/YnKap8IxWpV55xPC', 'Nikola', 'Nikolic', 'admin@example.com', true, false, 0, 0, '2017-10-01 18:57:58.508-07', 'blsk56oyh01');

INSERT INTO ROLE (name) VALUES ('ROLE_USER');
INSERT INTO ROLE (name) VALUES ('ROLE_AGENT');
INSERT INTO ROLE (name) VALUES ('ROLE_ADMIN');

INSERT INTO USER_ROLE (user_id, role_id) VALUES (1, 1);
INSERT INTO USER_ROLE (user_id, role_id) VALUES (2, 1);
INSERT INTO USER_ROLE (user_id, role_id) VALUES (2, 2);
INSERT INTO USER_ROLE (user_id, role_id) VALUES (2, 3);

INSERT INTO PERMISSION (name) VALUES ('PERMISSION_TEST');
INSERT INTO PERMISSION (name) VALUES ('CREATE_OGLAS');
INSERT INTO PERMISSION (name) VALUES ('MANAGE_USERS');

INSERT INTO ROLE_PERMISSION (role_id, permission_id) values (1, 2);
INSERT INTO ROLE_PERMISSION (role_id, permission_id) values (2, 2);
INSERT INTO ROLE_PERMISSION (role_id, permission_id) values (3, 1);
INSERT INTO ROLE_PERMISSION (role_id, permission_id) values (3, 2);
INSERT INTO ROLE_PERMISSION (role_id, permission_id) values (3, 3);

