CREATE SEQUENCE birthday_id_seq;
CREATE TABLE birthday
(
    id integer NOT NULL DEFAULT nextval('birthday_id_seq'),
    userId integer NOT NULL,
    userName text DEFAULT NULL ,
    chatId integer NOT NULL,
    birthdayDate date DEFAULT NULL,
    responsible boolean NOT NULL,
    PRIMARY KEY (id)
);