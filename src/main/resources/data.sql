CREATE SEQUENCE birthday_id_seq;
CREATE TABLE birthday
(
    id bigint NOT NULL DEFAULT nextval('birthday_id_seq'),
    user_id bigint NOT NULL,
    user_name text DEFAULT NULL ,
    chat_id bigint NOT NULL,
    birthday_date date DEFAULT NULL,
    responsible boolean NOT NULL,
    PRIMARY KEY (id)
);
CREATE UNIQUE INDEX birthday_userChatId ON birthday (user_id, chat_id);
CREATE UNIQUE INDEX birthday_userBirthday ON birthday (user_id, birthday_date);