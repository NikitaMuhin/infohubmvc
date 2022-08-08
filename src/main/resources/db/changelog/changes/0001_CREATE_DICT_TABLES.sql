--liquibase formatted sql

--changeset INFOHUB:1
CREATE TABLE service_type
(
    code character varying(50) COLLATE pg_catalog."default",
    description character varying(250) COLLATE pg_catalog."default",
    CONSTRAINT service_type_pkey PRIMARY KEY (code)
);


INSERT INTO service_type(code, description) VALUES ('Salubritate', 'Salubritate');
INSERT INTO service_type(code, description) VALUES ('Lift', 'Lift');
INSERT INTO service_type(code, description) VALUES ('Energie scara', 'Energie scara');
INSERT INTO service_type(code, description) VALUES ('Curatenie scara', 'Curatenie scara');
INSERT INTO service_type(code, description) VALUES ('Apa si canalizare', 'Apa si canalizare');

CREATE TABLE status_code
(
    code character varying(50) COLLATE pg_catalog."default",
    description character varying(250) COLLATE pg_catalog."default",
    CONSTRAINT status_code_pkey PRIMARY KEY (code)
);


INSERT INTO status_code(code, description) VALUES ('IN_OPERATION','In operation');
INSERT INTO status_code(code, description) VALUES ('ACTIVE','Active');
INSERT INTO status_code(code, description) VALUES ('IN_REPARATIE','In reparatie');


CREATE TABLE calculation_type
(
    code character varying(50) COLLATE pg_catalog."default",
    description character varying(250) COLLATE pg_catalog."default",
    CONSTRAINT calculation_type_pkey PRIMARY KEY (code)
);

INSERT INTO calculation_type(code, description) VALUES ('PART_SHARE', 'PART_SHARE');
INSERT INTO calculation_type(code, description) VALUES ('COUNTER', 'COUNTER');
INSERT INTO calculation_type(code, description) VALUES ('AREA','AREA');

CREATE TABLE discount_type
(
    code character varying(50) COLLATE pg_catalog."default",
    description character varying(250) COLLATE pg_catalog."default",
    CONSTRAINT discount_type_pkey PRIMARY KEY (code)
);

INSERT INTO discount_type(code, description) VALUES ('veteran', 'veteran');
INSERT INTO discount_type(code, description) VALUES ('pensionar', 'pensionar');


CREATE TABLE property_type
(
    code character varying(50) COLLATE pg_catalog."default",
    description character varying(250) COLLATE pg_catalog."default",
    CONSTRAINT property_type_pkey PRIMARY KEY (code)
);

INSERT INTO property_type(code, description) VALUES ('GARAGE', 'GARAGE');
INSERT INTO property_type(code, description) VALUES ('OFFICE', 'OFFICE');
INSERT INTO property_type(code, description) VALUES ('DEBARA', 'DEBARA');
INSERT INTO property_type(code, description) VALUES ('APARTMENT', 'APARTMENT');

CREATE TABLE units
(
    code character varying(50),
    description character varying(250),
    CONSTRAINT unitati_pkey PRIMARY KEY (code)
);


INSERT INTO units(code, description) VALUES ('m3', 'Metri cubi');
INSERT INTO units(code, description) VALUES ('m2', 'Metri patrati');
INSERT INTO units(code, description) VALUES ('PRC', 'Procente');
INSERT INTO units(code, description) VALUES ('PRS', 'Persoane');
INSERT INTO units(code, description) VALUES ('VALUE', 'Valoare Lei');


