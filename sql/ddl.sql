drop table if exists feeling CASCADE;
DROP TABLE IF EXISTS feeling_type CASCADE;
drop table if exists user CASCADE;
create table user
(
    id       INT UNSIGNED NOT NULL AUTO_INCREMENT,
    name     VARCHAR(256),
    password VARCHAR(256),
    PRIMARY KEY (id),
    UNIQUE KEY (name)
);

CREATE TABLE feeling_type
(
    id   INT UNSIGNED NOT NULL AUTO_INCREMENT,
    name VARCHAR(256),
    PRIMARY KEY (id),
    UNIQUE KEY (name)
);

CREATE TABLE feeling
(
    id                INT UNSIGNED NOT NULL AUTO_INCREMENT,
    created_date      DATE UNIQUE DEFAULT (CURRENT_DATE),
    created_timestamp TIMESTAMP   DEFAULT CURRENT_TIMESTAMP,
    user_id           INT UNSIGNED,
    type_id           INT UNSIGNED,
    description       VARCHAR(1024),
    FOREIGN KEY (user_id) REFERENCES user (id) ON DELETE NO ACTION,
    FOREIGN KEY (type_id) REFERENCES feeling_type (id) ON DELETE NO ACTION,
    PRIMARY KEY (id)
);
