--Insert a la tabla regions
insert into regions (name) values ('America');
insert into regions (name) values ('Europa');
insert into regions (name) values ('Asia');
insert into regions (name) values ('Africa');

--Insert a la tabla customers
insert into customers (name, lastname, email, created_at, region_id) values ('Juan', 'Perez', 'juan@example.com', '2021-01-01', 1);
insert into customers (name, lastname, email, created_at, region_id) values ('Maria', 'Lopez', 'maria@example.com', '2022-05-01', 1);
insert into customers (name, lastname, email, created_at, region_id) values ('Pedro', 'Gomez', 'pedro@example.com', '2023-01-01', 2);
insert into customers (name, lastname, email, created_at, region_id) values ('Ana', 'Rodriguez', 'ana@example.com', '2024-01-01', 3);
