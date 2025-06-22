-- This script is used to populate the database with initial data after migration

delete from restaurant_payment_methods;
delete from order_items;
delete from products;
delete from group_users;
delete from group_permissions;
delete from orders;

delete from restaurants;
delete from users;
delete from permissions;
delete from groups;
delete from payment_methods;
delete from cities;
delete from states;
delete from kitchens;

ALTER SEQUENCE cities_id_seq RESTART WITH 1;
ALTER SEQUENCE states_id_seq RESTART WITH 1;
ALTER SEQUENCE payment_methods_id_seq RESTART WITH 1;
ALTER SEQUENCE groups_id_seq RESTART WITH 1;
ALTER SEQUENCE permissions_id_seq RESTART WITH 1;
ALTER SEQUENCE products_id_seq RESTART WITH 1;
ALTER SEQUENCE orders_id_seq RESTART WITH 1;
ALTER SEQUENCE order_items_id_seq RESTART WITH 1;

 insert into kitchens (id, name) values
   ('11111111-1111-1111-1111-111111111111', 'Italian'),
   ('22222222-2222-2222-2222-222222222222', 'Chinese'),
   ('33333333-3333-3333-3333-333333333333', 'Mexican');

 insert into states (id, name) values
   (9900, 'California'),
   (9901, 'Texas'),
   (9902, 'New York');

 insert into cities (id, name, state_id) values
   (9900, 'Los Angeles', 9900),
   (9901, 'Houston', 9901),
   (9902, 'New York City', 9902);

 insert into payment_methods (id, name) values
   (9900, 'Credit Card'),
   (9901, 'Cash'),
   (9902, 'PayPal');

 insert into groups (id, name) values
   (9900, 'Admin'),
   (9901, 'User');

 insert into permissions (id, name) values
   (9900, 'READ'),
   (9901, 'WRITE'),
   (9902, 'DELETE');

 insert into group_permissions (group_id, permission_id) values
   (9900, 9900),
   (9900, 9901),
   (9900, 9902),
   (9901, 9900);

 insert into users (id, name, email, password, date_created, date_updated) values
   ('aaaaaaa1-aaaa-aaaa-aaaa-aaaaaaaaaaa1', 'John Doe', 'email@email.com', 'password123', now(), now()),
   ('aaaaaaa2-aaaa-aaaa-aaaa-aaaaaaaaaaa2', 'Jane Smith', 'email2@email.com', 'password456', now(), now()),
   ('aaaaaaa3-aaaa-aaaa-aaaa-aaaaaaaaaaa3', 'Alice Johnson','email3@email.com', 'password789', now(), now());

 insert into group_users (group_id, user_id) values
   (9900, 'aaaaaaa1-aaaa-aaaa-aaaa-aaaaaaaaaaa1'),
   (9901, 'aaaaaaa2-aaaa-aaaa-aaaa-aaaaaaaaaaa2');

 insert into restaurants (id, kitchen_id, name, address_street, address_number, address_complement, address_neighborhood, address_zipcode, address_city_id, date_created, date_updated, delivery_fee) values
   ('bbbbbbb1-bbbb-bbbb-bbbb-bbbbbbbbbbb1', '11111111-1111-1111-1111-111111111111', 'Pasta Palace', '123 Pasta St', '1A', 'Near the fountain', 'Downtown', '90001', 9900, now(), now(), 5.00),
   ('bbbbbbb2-bbbb-bbbb-bbbb-bbbbbbbbbbb2', '22222222-2222-2222-2222-222222222222', 'Wok Wonders', '456 Wok Ave', '2B', '', 'Chinatown', '77001', 9901, now(), now(), 4.00),
   ('bbbbbbb3-bbbb-bbbb-bbbb-bbbbbbbbbbb3', '33333333-3333-3333-3333-333333333333', 'Taco Town', '789 Taco Blvd', '', '', 'South Side', '10001', 9902, now(), now(), 3.00);

 insert into products (id, restaurant_id, name, description, price, active) values
   (9900, 'bbbbbbb1-bbbb-bbbb-bbbb-bbbbbbbbbbb1', 'Spaghetti Carbonara', 'Classic Italian pasta dish', 12.99, true),
   (9901, 'bbbbbbb2-bbbb-bbbb-bbbb-bbbbbbbbbbb2', 'Kung Pao Chicken', 'Spicy stir-fried chicken with peanuts', 10.99, true),
   (9902, 'bbbbbbb3-bbbb-bbbb-bbbb-bbbbbbbbbbb3', 'Beef Taco', 'Delicious beef taco with fresh toppings', 8.99, true);

 insert into restaurant_payment_methods (restaurant_id, payment_method_id) values
   ('bbbbbbb1-bbbb-bbbb-bbbb-bbbbbbbbbbb1', 9900),
   ('bbbbbbb2-bbbb-bbbb-bbbb-bbbbbbbbbbb2', 9901),
   ('bbbbbbb3-bbbb-bbbb-bbbb-bbbbbbbbbbb3', 9902);