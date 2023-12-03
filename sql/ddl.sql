drop table if exists Feeling CASCADE;
DROP TABLE IF EXISTS FeelingType CASCADE;
drop table if exists User CASCADE;
create table User
(
    id       INT UNSIGNED NOT NULL AUTO_INCREMENT,
    name     VARCHAR(256),
    password VARCHAR(256),
    PRIMARY KEY (id),
    UNIQUE KEY(name)
);

CREATE TABLE FeelingType
(
    id   INT UNSIGNED NOT NULL AUTO_INCREMENT,
    name VARCHAR(256),
    PRIMARY KEY (id),
    UNIQUE KEY(name)
);

CREATE TABLE `Feeling`
(
    id           INT UNSIGNED NOT NULL AUTO_INCREMENT,
    created_date DATETIME NOT NULL,
    user_id      INT UNSIGNED NOT NULL,
    type_id      INT UNSIGNED NOT NULL,
    description  VARCHAR(1024),
    FOREIGN KEY (user_id) REFERENCES User (id) ON DELETE NO ACTION,
    FOREIGN KEY (type_id) REFERENCES FeelingType (id) ON DELETE NO ACTION,
    PRIMARY KEY (id)
);