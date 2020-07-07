-- Lozinke su hesovane pomocu BCrypt algoritma https://www.dailycred.com/article/bcrypt-calculator
-- Lozinka za oba user-a je 1234567899

INSERT INTO USERS (username, password, first_name, last_name, email, activated, accepted, blocked, blocked_postavljanje_oglasa, blocked_slanje_zahteva, canceled, owes, deleted, last_password_reset_date, salt)
	VALUES ('userA', '$2a$10$Fbq6q3mR.j4bgoAIKTJbYOr6ccjsMNZdmlKwRikJP8gLkx60jaSmG', 'Marko', 'Markovic', 'user@example.com', true, true, false, false, false, 0, 0, false, '2017-10-01 21:58:58.508-07', 'a0s8hgy28h1');
INSERT INTO USERS (username, password, first_name, last_name, email, activated, accepted, blocked, blocked_postavljanje_oglasa, blocked_slanje_zahteva, canceled, owes, deleted, last_password_reset_date, salt)
	VALUES ('admin', '$2a$10$eH8zX6i0FSIWRj4h9fSMdOJFCrMLuoI8aJIp/YnKap8IxWpV55xPC', 'Nikola', 'Nikolic', 'admin@example.com', true, true, false, false, false, 0, 0, false, '2017-10-01 18:57:58.508-07', 'blsk56oyh01');
INSERT INTO USERS (username, password, first_name, last_name, email, activated, accepted, blocked, blocked_postavljanje_oglasa, blocked_slanje_zahteva, canceled, owes, deleted, last_password_reset_date, salt)
	VALUES ('agent', '$2a$10$l.DxIIOAH4gwZ0H4KbB/ault2KsVQHUMY9CYXlJ7/yRnTOA6eIMU.', 'Nikola', 'Nikolic', 'admin@example.com', true, true, false, false, false, 0, 0, false, '2017-10-01 18:57:58.508-07', 'los8gm50fd1');
INSERT INTO USERS (username, password, first_name, last_name, email, activated, accepted, blocked, blocked_postavljanje_oglasa, blocked_slanje_zahteva, canceled, owes, deleted, last_password_reset_date, salt)
	VALUES ('pera', '$2a$10$Fbq6q3mR.j4bgoAIKTJbYOr6ccjsMNZdmlKwRikJP8gLkx60jaSmG', 'Pera', 'Peric', 'pera@example.com', false, false, false, false, false, 0, 0, false, '2017-10-01 21:58:58.508-07', 'a0s8hgy28h1');

	
INSERT INTO ROLE (name) VALUES ('ROLE_USER');
INSERT INTO ROLE (name) VALUES ('ROLE_AGENT');
INSERT INTO ROLE (name) VALUES ('ROLE_ADMIN');
INSERT INTO ROLE (name) VALUES ('ROLE_USER_LIMITED');

INSERT INTO USER_ROLE (user_id, role_id) VALUES (1, 1);
INSERT INTO USER_ROLE (user_id, role_id) VALUES (2, 3);
INSERT INTO USER_ROLE (user_id, role_id) VALUES (3, 2);

INSERT INTO PERMISSION (name) VALUES ('PERMISSION_TEST');
INSERT INTO PERMISSION (name) VALUES ('CREATE_OGLAS');
INSERT INTO PERMISSION (name) VALUES ('MANAGE_USERS');
INSERT INTO PERMISSION (name) VALUES ('MANAGE_CHAT');
INSERT INTO PERMISSION (name) VALUES ('MANAGE_SIFRARNIK');
INSERT INTO PERMISSION (name) VALUES ('MANAGE_CENOVNIK');
INSERT INTO PERMISSION (name) VALUES ('MANAGE_OCENA');
INSERT INTO PERMISSION (name) VALUES ('MANAGE_OGLAS');
INSERT INTO PERMISSION (name) VALUES ('MANAGE_ZAHTEV');
INSERT INTO PERMISSION (name) VALUES ('MANAGE_IZVESTAJ');
INSERT INTO PERMISSION (name) VALUES ('MANAGE_OCENA_ADMIN');
-- USER
INSERT INTO ROLE_PERMISSION (role_id, permission_id) values (1, 2); -- CREATE_OGLAS
INSERT INTO ROLE_PERMISSION (role_id, permission_id) values (1, 4); -- MANAGE_CHAT
INSERT INTO ROLE_PERMISSION (role_id, permission_id) values (1, 6); -- MANAGE_CENOVNIK
INSERT INTO ROLE_PERMISSION (role_id, permission_id) values (1, 7); -- MANAGE_OCENA
INSERT INTO ROLE_PERMISSION (role_id, permission_id) values (1, 8); -- MANAGE_OGLAS
INSERT INTO ROLE_PERMISSION (role_id, permission_id) values (1, 9); -- MANAGE_ZAHTEV
INSERT INTO ROLE_PERMISSION (role_id, permission_id) values (1, 10); -- MANAGE_IZVESTAJ

-- USER_LIMITED
INSERT INTO ROLE_PERMISSION (role_id, permission_id) values (1, 4); -- MANAGE_CHAT
INSERT INTO ROLE_PERMISSION (role_id, permission_id) values (1, 6); -- MANAGE_CENOVNIK
INSERT INTO ROLE_PERMISSION (role_id, permission_id) values (1, 7); -- MANAGE_OCENA
INSERT INTO ROLE_PERMISSION (role_id, permission_id) values (1, 9); -- MANAGE_ZAHTEV

-- AGENT
INSERT INTO ROLE_PERMISSION (role_id, permission_id) values (2, 2); -- CREATE_OGLAS
INSERT INTO ROLE_PERMISSION (role_id, permission_id) values (2, 4); -- MANAGE_CHAT
INSERT INTO ROLE_PERMISSION (role_id, permission_id) values (2, 6); -- MANAGE_CENOVNIK
INSERT INTO ROLE_PERMISSION (role_id, permission_id) values (2, 7); -- MANAGE_OCENA
INSERT INTO ROLE_PERMISSION (role_id, permission_id) values (2, 8); -- MANAGE_OGLAS
INSERT INTO ROLE_PERMISSION (role_id, permission_id) values (2, 9); -- MANAGE_ZAHTEV
INSERT INTO ROLE_PERMISSION (role_id, permission_id) values (2, 10); -- MANAGE_IZVESTAJ

-- ADMIN
INSERT INTO ROLE_PERMISSION (role_id, permission_id) values (3, 1); -- PERMISSION_TEST
INSERT INTO ROLE_PERMISSION (role_id, permission_id) values (3, 3); -- MANAGE_USERS
INSERT INTO ROLE_PERMISSION (role_id, permission_id) values (3, 5); -- MANAGE_SIFRARNIK
INSERT INTO ROLE_PERMISSION (role_id, permission_id) values (3, 11); -- MANAGE_OCENA_ADMIN

