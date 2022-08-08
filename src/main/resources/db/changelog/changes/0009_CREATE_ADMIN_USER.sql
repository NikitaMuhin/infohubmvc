
-- create user admin with admin
INSERT INTO users (username, password, first_name, last_name, status, date_created, email, phone_number)
VALUES ('admin', '$2a$10$gCGb3bMRnzwKOQzl6ptIYe3ilxZsWj8z9BTSII.jJAY9J90iv4.EW', 'Admin', 'Admin', 'ACTIVE', '2021-08-23 19:47:30', 'iurie.eni@gmail.com', '123456789');

INSERT INTO "users" ("username", "password", "first_name", "last_name", "email", "phone_number", "status", "date_created")
VALUES ('user', '$2a$10$oJPr/.WZb6LDp/kyc8yJkOaoztgKvghi6DLz9qC4XztklRPGumWJa', '', '', '', '', 'ACTIVE', '2022-01-13 15:29:14.035434');


-- grant all roles with permissions to admin user
insert into user_roles (user_id, role, view_permission, manage_permission)
select u.id, 'ADMIN', true, true from users u where u.username = 'admin';

-- grant all roles with permissions to admin user
insert into user_roles (user_id, role, view_permission, manage_permission)
select u.id, 'AUTH', true, true from users u where u.username = 'user';


-- create condominiums
INSERT INTO condominiums (name, registration_number, office_address, infohub_contract_nr, status, date_created, teller_id)
VALUES ('Melanin', '123', 'Cuza Voda 10', '34324', 'ACTIVE', '2021-08-22 18:53:36', (select u.id from users u where u.username='admin'));
INSERT INTO condominiums (name, registration_number, office_address, infohub_contract_nr, status, date_created, teller_id)
VALUES ('Valea Morilor', '123-4', 'Mircea cel Mare 12', '34324', 'ACTIVE', '2021-08-22 21:54:21', (select u.id from users u where u.username='admin'));
INSERT INTO condominiums (name, registration_number, office_address, infohub_contract_nr, status, date_created, teller_id)
VALUES ('Imperial', '12-34', 'Gh. Asachi 1', '253554', 'ACTIVE', '2021-08-22 23:21:04', (select u.id from users u where u.username='admin'));
INSERT INTO condominiums (name, registration_number, office_address, infohub_contract_nr, status, date_created, teller_id)
VALUES ('Cicago', '111111', 'Gh. Asachi 11/2', '253554', 'ACTIVE', '2021-08-22 18:53:36', (select u.id from users u where u.username='admin'));
INSERT INTO condominiums (name, registration_number, office_address, infohub_contract_nr, status, date_created, teller_id)
VALUES ('Tri Pokoinica', '23432', 'Gh. Asachi 55', '253554', 'ACTIVE', '2021-08-22 23:32:54', (select u.id from users u where u.username='admin'));
INSERT INTO condominiums (name, registration_number, office_address, infohub_contract_nr, status, date_created, teller_id)
VALUES ('Marusea', '4567465 ss', 'Gh. Asachi 58/7', '253554', 'ACTIVE', '2021-08-22 23:36:14', (select u.id from users u where u.username='admin'));

--create houses
INSERT INTO houses (condominium_id, name, house_address, number_blocks, number_floors, number_stairs, total_area, year_built, has_lift, status, teller_id)
VALUES ((select c.id from condominiums c where c.name = 'Melanin'), 'Melanin 23', 'Cuza Voda 12', 1, 9, 4, 1000000.00, 2001, true, 'IN_OPERATION', (select u.id from users u where u.username='admin'));
INSERT INTO houses (condominium_id, name, house_address, number_blocks, number_floors, number_stairs, total_area, year_built, has_lift, status, teller_id)
VALUES ((select c.id from condominiums c where c.name = 'Melanin'), 'Melanin 23', 'Cuza Voda 12', 1, 9, 4, 1000000.00, 2001, true, 'IN_OPERATION', (select u.id from users u where u.username='admin'));

INSERT INTO houses (condominium_id, name, house_address, number_blocks, number_floors, number_stairs, total_area, year_built, has_lift, status, teller_id)
VALUES ((select c.id from condominiums c where c.name = 'Valea Morilor'), 'Melanin 1', 'Cuza Voda 1', 1, 9, 4, 1000000.00, 2001, true, 'IN_OPERATION', (select u.id from users u where u.username='admin'));
INSERT INTO houses (condominium_id, name, house_address, number_blocks, number_floors, number_stairs, total_area, year_built, has_lift, status, teller_id)
VALUES ((select c.id from condominiums c where c.name = 'Valea Morilor'), 'Melanin 2', 'Cuza 1', 1, 9, 4, 1000000.00, 2001, true, 'IN_OPERATION', (select u.id from users u where u.username='admin'));

INSERT INTO houses (condominium_id, name, house_address, number_blocks, number_floors, number_stairs, total_area, year_built, has_lift, status, teller_id)
VALUES ((select c.id from condominiums c where c.name = 'Imperial'), 'Tot acolo 23', 'Cuza Voda 12', 1, 9, 4, 1000000.00, 2001, true, 'IN_OPERATION', (select u.id from users u where u.username='admin'));
INSERT INTO houses (condominium_id, name, house_address, number_blocks, number_floors, number_stairs, total_area, year_built, has_lift, status, teller_id)
VALUES ((select c.id from condominiums c where c.name = 'Imperial'), 'Mai jos de piata 123', 'Cuza Voda 12', 1, 9, 4, 1000000.00, 2001, true, 'IN_OPERATION', (select u.id from users u where u.username='admin'));

INSERT INTO houses (condominium_id, name, house_address, number_blocks, number_floors, number_stairs, total_area, year_built, has_lift, status, teller_id)
VALUES ((select c.id from condominiums c where c.name = 'Cicago'), 'Valea rapii 0', 'Cuza Voda 12', 1, 9, 4, 1000000.00, 2001, true, 'IN_OPERATION', (select u.id from users u where u.username='admin'));
INSERT INTO houses (condominium_id, name, house_address, number_blocks, number_floors, number_stairs, total_area, year_built, has_lift, status, teller_id)
VALUES ((select c.id from condominiums c where c.name = 'Cicago'), 'Lupoaica 23', 'Cuza Voda 12', 1, 9, 4, 1000000.00, 2001, true, 'IN_OPERATION', (select u.id from users u where u.username='admin'));

INSERT INTO houses (condominium_id, name, house_address, number_blocks, number_floors, number_stairs, total_area, year_built, has_lift, status, teller_id)
VALUES ((select c.id from condominiums c where c.name = 'Tri Pokoinica'), 'Armeana 3', 'Cuza Voda 12', 1, 9, 4, 1000000.00, 2001, true, 'IN_OPERATION', (select u.id from users u where u.username='admin'));
INSERT INTO houses (condominium_id, name, house_address, number_blocks, number_floors, number_stairs, total_area, year_built, has_lift, status, teller_id)
VALUES ((select c.id from condominiums c where c.name = 'Tri Pokoinica'), 'Starovo 23', 'Cuza Voda 12', 1, 9, 4, 1000000.00, 2001, true, 'IN_OPERATION', (select u.id from users u where u.username='admin'));

INSERT INTO houses (condominium_id, name, house_address, number_blocks, number_floors, number_stairs, total_area, year_built, has_lift, status, teller_id)
VALUES ((select c.id from condominiums c where c.name = 'Marusea'), 'Marusea 1', 'Maria Dragan 1', 1, 9, 4, 1000000.00, 2001, true, 'IN_OPERATION', (select u.id from users u where u.username='admin'));
INSERT INTO houses (condominium_id, name, house_address, number_blocks, number_floors, number_stairs, total_area, year_built, has_lift, status, teller_id)
VALUES ((select c.id from condominiums c where c.name = 'Marusea'), 'Marusea 2', 'Maria Voda 12', 1, 9, 4, 1000000.00, 2001, true, 'IN_OPERATION', (select u.id from users u where u.username='admin'));


