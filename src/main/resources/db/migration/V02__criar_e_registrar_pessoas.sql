CREATE TABLE person (
    id BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL,
    active BOOLEAN NOT NULL,
    street VARCHAR(50),
    number VARCHAR(10),
    complement VARCHAR(50),
    neighbourhood VARCHAR(50),
    zip_code VARCHAR(20),
    city VARCHAR(20),
    state VARCHAR(2)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


INSERT INTO person (name, active, street, number, complement, neighbourhood, zip_code, city, state)
    VALUES ('Fernanda Marcela Isis Monteiro', TRUE, 'Rua Jacó Lamim', '835', '', 'São Judas', '88303500', 'Itajaí', 'SC');
INSERT INTO person (name, active, street, number, complement, neighbourhood, zip_code, city, state)
    VALUES ('Isabelly Heloise Andreia Galvão', TRUE, 'Rua Desembargador Adalberto Correia Lima', '348', '', 'Planalto', '64050260', 'Teresina', 'PI');
INSERT INTO person (name, active, street, number, complement, neighbourhood, zip_code, city, state)
    VALUES ('Débora Carla Mendes', TRUE, 'Rua Guararapes', '385', '', 'Porto do Centro', '64060390', 'Teresina', 'PI');
INSERT INTO person (name, active, street, number, complement, neighbourhood, zip_code, city, state)
    VALUES ('Diego Emanuel Baptista', TRUE, 'Rua Sátiro Rodrigues da Silva', '725', '', 'Santo Antônio', '64033350', 'Teresina', 'PI');
INSERT INTO person (name, active, street, number, complement, neighbourhood, zip_code, city, state)
    VALUES ('Letícia Malu da Rosa', TRUE, 'Rua João Paulo I', '113', '', 'Conceição', '76808398', 'Porto Velho', 'RO');
INSERT INTO person (name, active, street, number, complement, neighbourhood, zip_code, city, state)
    VALUES ('Melissa Sara Stefany', TRUE, 'Quadra Quadra 302 Conjunto 4', '153', '', 'Setor Residencial Oeste (São Sebastião)', '71692770', 'Brasília', 'DF');
INSERT INTO person (name, active, street, number, complement, neighbourhood, zip_code, city, state)
    VALUES ('Heitor Mateus da Rosa', TRUE, 'Rua Severa Romana', '711', '', 'Jangurussu', '60865120', 'Fortaleza', 'CE');
INSERT INTO person (name, active)
    VALUES ('Mário Marcelo Baptista', TRUE);
INSERT INTO person (name, active)
    VALUES ('Márcio Alexandre Yuri Costa', TRUE);
INSERT INTO person (name, active)
    VALUES ('Brenda Hadassa Bianca', TRUE);