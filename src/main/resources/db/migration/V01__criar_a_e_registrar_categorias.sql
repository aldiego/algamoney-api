CREATE TABLE category (
    id BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO category (name) VALUES ('Entertainment');
INSERT INTO category (name) VALUES ('Supply');
INSERT INTO category (name) VALUES ('Market');
INSERT INTO category (name) VALUES ('Drugstore');
INSERT INTO category (name) VALUES ('Others');
