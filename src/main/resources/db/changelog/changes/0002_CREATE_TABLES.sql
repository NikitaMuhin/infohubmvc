--liquibase formatted sql

--changeset INFOHUB:1

create table USERS (
	ID bigserial primary key,
	GROUP_ID bigint,
	USERNAME varchar(100) not null,
	password varchar(100) not null,
	FIRST_NAME varchar(50) not null,
	LAST_NAME varchar(50) not null,
	EMAIL VARCHAR(100) not null,
	PHONE_NUMBER VARCHAR(50),
	STATUS varchar(20) CHECK (STATUS IN ('ACTIVE', 'BLOCKED', 'CLOSED')) DEFAULT 'ACTIVE',
	DATE_CREATED TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	CONSTRAINT "UNIQUE_USERNAME" UNIQUE (USERNAME)
);

create table CONDOMINIUMS (
	ID bigserial primary key,
	NAME varchar(250) not null,
	REGISTRATION_NUMBER varchar(50) not null,
	OFFICE_ADDRESS varchar(500)	not null,
	INFOHUB_CONTRACT_NR varchar(50),
	STATUS varchar(20),  -- CHECK (STATUS IN ('ACTIVE', 'CLOSED')) DEFAULT 'ACTIVE',
	DATE_CREATED TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	TELLER_ID bigint not null,
	CONSTRAINT "FK_HOUSE_USER" FOREIGN KEY (TELLER_ID) REFERENCES USERS(ID)
);

create table HOUSES (
	ID bigserial primary key,
	CONDOMINIUM_ID bigint not null,
	NAME varchar(250) not null,
	HOUSE_ADDRESS varchar(500) not null,
	NUMBER_BLOCKS bigint not null,
	NUMBER_FLOORS bigint not null,
	NUMBER_STAIRS bigint,
	TOTAL_AREA numeric(19, 2) not null,
	YEAR_BUILT bigint not null,
	has_Lift boolean,
	STATUS varchar(20),  -- CHECK (STATUS IN ('InOperation', 'UnderConstruction', 'InRepair')) DEFAULT 'InOperation',
	DATE_CREATED TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	TELLER_ID bigint not null,
	CONSTRAINT "FK_HOUSE_CONDOMINIUM" FOREIGN KEY (CONDOMINIUM_ID) REFERENCES CONDOMINIUMS(ID),
	CONSTRAINT "FK_CONDOMINIUM_USER" FOREIGN KEY (TELLER_ID) REFERENCES USERS(ID)
);

create table PROPERTIES (
	ID bigserial primary key,
	HOUSE_ID bigint not null,
	PROPERTY_TYPE varchar(50) not null,  -- CHECK (PROPERTY_TYPE IN ('APARTMENT', 'OFFICE', 'GARAGE', 'DEBARA')) DEFAULT 'APARTMENT',
	FIRST_NAME varchar(50) not null,
	LAST_NAME varchar(50) not null,
	FLOOR bigint not null,
	ADDRESS varchar(250) not null,
	PHONE_NUMBERS varchar(50),
	EMAIL varchar(50),
	STAIRCASE int not null,
	AREA numeric(19, 2) not null,
	NUMBER_ROOMS int not null,
	NUMBER_MEMBERS int not null,
	USER_GROUP_ID bigint,
	DATE_CREATED TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	TELLER_ID bigint not null,
	CONSTRAINT "FK_PROPERTY_HOUSE" FOREIGN KEY (HOUSE_ID) REFERENCES HOUSES(ID),
	CONSTRAINT "fk_property_type" FOREIGN KEY (property_type) REFERENCES property_type(code) ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT "FK_PROPERTY_USER" FOREIGN KEY (TELLER_ID) REFERENCES USERS(ID)
);

create table CALCULATION_FORMULA (
	ID bigserial primary key,
	CODE varchar(20) not null,
	DESCRIPTION varchar(250),
	CALCULATION_TYPE varchar(50) not null,
	FORMULA varchar(100) not null,
	DATE_FROM DATE NOT NULL,
	DATE_TO DATE NOT NULL,
	ACTIVE BOOLEAN NULL DEFAULT NULL,
	DATE_CREATED TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	TELLER_ID bigint not null,
	CONSTRAINT "UNIQUE_CALCULATION_FORMULA" UNIQUE (CODE),
	CONSTRAINT "FK_CALCULATION_FORMULA_USER" FOREIGN KEY (TELLER_ID) REFERENCES USERS(ID),
	CONSTRAINT "FK_calculation_formula_calculation_type" FOREIGN KEY (calculation_type) REFERENCES calculation_type (code) ON UPDATE NO ACTION ON DELETE NO ACTION
);

create table DISCOUNTS (
	ID bigserial primary key,
	DISCOUNT_CODE varchar(50) not null,
	DESCRIPTION varchar(250),
	DISCOUNT_VALUE numeric(19, 2),
	DISCOUNT_UNIT varchar(50),
	DATE_FROM DATE,
	DATE_TO DATE,
	ACTIVE BOOLEAN NULL DEFAULT NULL,
	DATE_CREATED TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	TELLER_ID bigint not null,
	CONSTRAINT "FK_DISCOUNT_USER" FOREIGN KEY (TELLER_ID) REFERENCES USERS(ID),
	CONSTRAINT "FK_discounts_discount_type" FOREIGN KEY (discount_code) REFERENCES discount_type (code) ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT FK_discounts_units FOREIGN KEY (discount_unit) REFERENCES units(code)

);

create table CONSUMPTION_POINTS (
	ID bigserial primary key,
	PARENT_ID bigint,
	CONDOMINIUM_ID bigint,
	HOUSE_ID bigint,
	PROPERTY_ID bigint,
	CALCULATION_CODE varchar(50) not null,  -- CHECK (CALCULATION_TYPE IN ('COUNTER', 'PART_SHARE', 'AREA')) DEFAULT 'COUNTER',
	DISCOUNT_VALUE bigint,
	DISCOUNT_CODE varchar(50),  -- CHECK (DISCOUNT_REASON IN ('MilitaryService', 'DisabilityGroup')),
	COUNTER_NUMBER varchar(50),
	VALIDITY_DATE_COUNTER DATE,
	INITIAL_COUNTER_INDEX bigint,
	DATE_CREATED TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	TELLER_ID bigint not null,
	service_code VARCHAR(50) NOT NULL,
	CONSTRAINT "FK_CONSUMPTION_POINT_CONDOMINIUM" FOREIGN KEY (CONDOMINIUM_ID) REFERENCES CONDOMINIUMS(ID),
    CONSTRAINT "FK_CONSUMPTION_POINT_HOUSE" FOREIGN KEY (HOUSE_ID) REFERENCES HOUSES(ID),
	CONSTRAINT "FK_CONSUMPTION_POINT_PROPERTY" FOREIGN KEY (PROPERTY_ID) REFERENCES PROPERTIES(ID),
	CONSTRAINT "FK_consumption_points_calculation_type" FOREIGN KEY (calculation_code) REFERENCES calculation_type (code),
	CONSTRAINT "FK_CONSUMPTION_POINT_SERVICE_TYPE" FOREIGN KEY (SERVICE_code) REFERENCES service_type(code),
	CONSTRAINT "FK_CONSUMPTION_POINT_USER" FOREIGN KEY (TELLER_ID) REFERENCES USERS(ID)
);

create table SERVICES (
	ID bigserial primary key,
	NAME varchar(50) not null,
	CONTRACT_NUMBER varchar(20),
	SERVICE_TYPE varchar(50) not null,
	TARIF_UNIT bigint,
	TARIF bigint,
	DATE_FROM DATE,
	DATE_TO DATE,
	DATE_CREATED TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	TELLER_ID bigint not null,
	CONSTRAINT "FK_SERVICE_USER" FOREIGN KEY (TELLER_ID) REFERENCES USERS(ID),
    CONSTRAINT "FK_services_service_type" FOREIGN KEY (service_type) REFERENCES service_type (code) ON UPDATE NO ACTION ON DELETE NO ACTION
);

create table BENEFICIARIES (
	ID bigserial primary key,
	CONDOMINIUM_ID bigint not null,
	HOUSE_ID bigint not null,
	SERVICE_NAME varchar(50) not null,
	SERVICE_ID bigint not null,
	CALCULATION_CODE varchar(20), -- CHECK (CALCULATION_TYPE IN ('FixedSum', 'Counter')),
	TARIF numeric(19, 2),
	INITIAL_COUNTER_INDEX bigint,
	DATE_CREATED TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	TELLER_ID bigint not null,
	CONSTRAINT "FK_BENEFICIARY_CONDOMINIUM" FOREIGN KEY (CONDOMINIUM_ID) REFERENCES CONDOMINIUMS(ID),
	CONSTRAINT "FK_BENEFICIARY_USER" FOREIGN KEY (TELLER_ID) REFERENCES USERS(ID)
);

create table INVOICES (
	ID bigserial primary key,
	CONDOMINIUM_ID bigint not null,
	INV_MONTH int not null,
	INV_YEAR int not null,
	BENEFICIARY_ID bigint not null,
	CALCULATED_AMOUNT numeric(19, 2) not null,
	DEBT numeric(19, 2),
	PENALTY numeric(19, 2),
	DATE_CREATED TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	TELLER_ID bigint not null,
	CONSTRAINT "FK_INVOICE_CONDOMINIUM" FOREIGN KEY (CONDOMINIUM_ID) REFERENCES CONDOMINIUMS(ID),
	CONSTRAINT "FK_INVOICE_BENEFICIARY" FOREIGN KEY (BENEFICIARY_ID) REFERENCES BENEFICIARIES(ID),
	CONSTRAINT "FK_INVOICES_USER" FOREIGN KEY (TELLER_ID) REFERENCES USERS(ID)
);

create table INVOICE_ELEMENTS (
    ID bigserial primary key,
	INVOICE_ID bigint not null,
	CONSUMPTION_POINT_ID bigint	not null,
	DATE_FROM DATE not null,
	PAY_UNTIL DATE not null,
	INDICE_START bigint,
	INDICE_END bigint,
	UNT_COST numeric(19, 2),
	UNITS numeric(19, 2),
	CALCULATED_AMOUNT numeric(19, 2) not null,
	DEBT numeric(19, 2),
	PENALTY numeric(19, 2),
	DATE_CREATED TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	TELLER_ID bigint not null,
	CONSTRAINT "FK_INVOICES_ELEMENT_INVOICE" FOREIGN KEY (INVOICE_ID) REFERENCES INVOICES(ID),
	CONSTRAINT "FK_INVOICES_ELEMENT_CONSUMPTION_POINT" FOREIGN KEY (CONSUMPTION_POINT_ID) REFERENCES CONSUMPTION_POINTS(ID),
	CONSTRAINT "FK_INVOICES_ELEMENT_USER" FOREIGN KEY (TELLER_ID) REFERENCES USERS(ID)
);

create table CONDOMINIUM_ADMINISTRATORS (
	ID bigserial primary key,
	CONDOMINIUM_ID bigint not null,
	JOB_FUNCTION varchar(20) CHECK (JOB_FUNCTION IN ('ADMINISTRATOR', 'COUNSELORS', 'CENSOR')),
	FIRST_NAME varchar(50) not null,
	LAST_NAME varchar(50) not null,
	DATE_START DATE,
	DATE_END DATE,
	EMAIL varchar(50),
	PHONE_NUMBERS varchar(50),
	DATE_CREATED TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	TELLER_ID bigint not null,
    CONSTRAINT "FK_CONDOMINIUM_ADMINISTRATORS_CONDOMINIUM" FOREIGN KEY (CONDOMINIUM_ID) REFERENCES CONDOMINIUMS(ID),
	CONSTRAINT "FK_CONDOMINIUM_ADMINISTRATORS_USER" FOREIGN KEY (TELLER_ID) REFERENCES USERS(ID)
);



CREATE TABLE
    USER_ROLES
    (
        ID bigserial primary key,
        USER_ID BIGINT NOT NULL,
        ROLE varchar(50) not null,
        VIEW_PERMISSION BOOLEAN DEFAULT true NOT NULL,
        MANAGE_PERMISSION BOOLEAN DEFAULT false NOT NULL,
        CONSTRAINT fkhfh9dx7w3ubf1co1vdev94g3f FOREIGN KEY (USER_ID) REFERENCES "users" ("id")
    );

CREATE INDEX ON USER_ROLES (USER_ID ASC);


create table USER_ACCESS_RIGHTS (
	ID bigserial primary key,
	USER_ID bigint not null,
	CONDOMINIUM_ID bigint not null,
	HOUSE_ID bigint not null,
	PROPERTY_ID bigint not null,
	VIEW_PERMISSION BOOLEAN DEFAULT true NOT NULL,
    MANAGE_PERMISSION BOOLEAN DEFAULT false NOT NULL,
	DATE_CREATED TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	TELLER_ID bigint not null,
	CONSTRAINT "FK_USER_RIGHT_USER" FOREIGN KEY (USER_ID) REFERENCES USERS(ID) ON DELETE CASCADE,
	CONSTRAINT "FK_USER_RIGHT_CONDOMINIUM" FOREIGN KEY (CONDOMINIUM_ID) REFERENCES CONDOMINIUMS(ID) ON DELETE CASCADE,
	CONSTRAINT "FK_USER_RIGHT_HOUSE" FOREIGN KEY (HOUSE_ID) REFERENCES HOUSES(ID) ON DELETE CASCADE,
	CONSTRAINT "FK_USER_RIGHT_PROPERTY" FOREIGN KEY (PROPERTY_ID) REFERENCES PROPERTIES(ID) ON DELETE CASCADE,
	CONSTRAINT "FK_USER_RIGHT_TELLER" FOREIGN KEY (TELLER_ID) REFERENCES USERS(ID)
);
