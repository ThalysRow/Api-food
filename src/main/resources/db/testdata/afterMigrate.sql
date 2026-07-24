-- This script is used to populate the database with initial data after migration

delete from restaurant_payment_methods;
delete from order_items;
delete from photo_product;
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

-- All integer-column sequences restart at 1 (Postgres default MINVALUE).
-- Test ids (1, 2, 3...) are explicit; the setval below pushes each sequence
-- above the highest id, avoiding collisions when the DB generates ids automatically.
ALTER SEQUENCE cities_id_seq RESTART WITH 1;
ALTER SEQUENCE states_id_seq RESTART WITH 1;
ALTER SEQUENCE payment_methods_id_seq RESTART WITH 1;
ALTER SEQUENCE groups_id_seq RESTART WITH 1;
ALTER SEQUENCE permissions_id_seq RESTART WITH 1;
ALTER SEQUENCE products_id_seq RESTART WITH 1;
ALTER SEQUENCE orders_id_seq RESTART WITH 1;
ALTER SEQUENCE order_items_id_seq RESTART WITH 1;

-- Fixed UUIDs (UUID columns do not use sequences)
 insert into kitchens (id, name) values
   ('11111111-1111-1111-1111-111111111111', 'Italian'),
   ('22222222-2222-2222-2222-222222222222', 'Chinese'),
   ('33333333-3333-3333-3333-333333333333', 'Mexican');

 insert into states (id, name) values
   (1, 'California'),
   (2, 'Texas'),
   (3, 'New York');

 insert into cities (id, name, state_id) values
   (1, 'Los Angeles', 1),
   (2, 'Houston', 2),
   (3, 'New York City', 3);

 insert into payment_methods (id, name) values
   (1, 'Credit Card'),
   (2, 'Cash'),
   (3, 'PayPal');

 insert into groups (id, name) values
   (1, 'Admin'),
   (2, 'User');

 insert into permissions (id, name) values
   (1, 'READ'),
   (2, 'WRITE'),
   (3, 'DELETE');

 insert into group_permissions (group_id, permission_id) values
   (1, 1),
   (1, 2),
   (1, 3),
   (2, 1);

 insert into users (id, name, email, password, date_created, date_updated) values
   ('aaaaaaa1-aaaa-aaaa-aaaa-aaaaaaaaaaa1', 'John Doe', 'email@email.com', 'password123', now(), now()),
   ('aaaaaaa2-aaaa-aaaa-aaaa-aaaaaaaaaaa2', 'Jane Smith', 'email2@email.com', 'password456', now(), now()),
   ('aaaaaaa3-aaaa-aaaa-aaaa-aaaaaaaaaaa3', 'Alice Johnson','email3@email.com', 'password789', now(), now());

 insert into group_users (group_id, user_id) values
   (1, 'aaaaaaa1-aaaa-aaaa-aaaa-aaaaaaaaaaa1'),
   (2, 'aaaaaaa2-aaaa-aaaa-aaaa-aaaaaaaaaaa2');

 insert into restaurants (id, kitchen_id, name, address_street, address_number, address_complement, address_neighborhood, address_zipcode, address_city_id, date_created, date_updated, delivery_fee) values
   ('bbbbbbb1-bbbb-bbbb-bbbb-bbbbbbbbbbb1', '11111111-1111-1111-1111-111111111111', 'Pasta Palace', '123 Pasta St', '1A', 'Near the fountain', 'Downtown', '90001', 1, now(), now(), 5.00),
   ('bbbbbbb2-bbbb-bbbb-bbbb-bbbbbbbbbbb2', '22222222-2222-2222-2222-222222222222', 'Wok Wonders', '456 Wok Ave', '2B', '', 'Chinatown', '77001', 2, now(), now(), 4.00),
   ('bbbbbbb3-bbbb-bbbb-bbbb-bbbbbbbbbbb3', '33333333-3333-3333-3333-333333333333', 'Taco Town', '789 Taco Blvd', '', '', 'South Side', '10001', 3, now(), now(), 3.00);

 insert into products (id, restaurant_id, name, description, price, active) values
   (1, 'bbbbbbb1-bbbb-bbbb-bbbb-bbbbbbbbbbb1', 'Spaghetti Carbonara', 'Classic Italian pasta dish', 12.99, true),
   (2, 'bbbbbbb2-bbbb-bbbb-bbbb-bbbbbbbbbbb2', 'Kung Pao Chicken', 'Spicy stir-fried chicken with peanuts', 10.99, true),
   (3, 'bbbbbbb3-bbbb-bbbb-bbbb-bbbbbbbbbbb3', 'Beef Taco', 'Delicious beef taco with fresh toppings', 8.99, true),
   (4, 'bbbbbbb1-bbbb-bbbb-bbbb-bbbbbbbbbbb1', 'Lasagna Bolognese', 'Rich meat sauce pasta, currently off the menu', 14.99, false),
   (5, 'bbbbbbb2-bbbb-bbbb-bbbb-bbbbbbbbbbb2', 'Sweet and Sour Pork', 'Temporarily unavailable dish', 11.99, false),
   (6, 'bbbbbbb3-bbbb-bbbb-bbbb-bbbbbbbbbbb3', 'Fish Taco', 'Seasonal item, not active right now', 9.49, false);

 insert into restaurant_payment_methods (restaurant_id, payment_method_id) values
   ('bbbbbbb1-bbbb-bbbb-bbbb-bbbbbbbbbbb1', 1),
   ('bbbbbbb2-bbbb-bbbb-bbbb-bbbbbbbbbbb2', 2),
   ('bbbbbbb3-bbbb-bbbb-bbbb-bbbbbbbbbbb3', 3);

insert into orders (id, subtotal, delivery_fee, total_value, status,
                    restaurant_id, user_id, payment_method_id,
                    address_city_id, address_zipcode, address_street,
                    address_number, address_complement, address_neighborhood,
                    date_created, date_confirmed)
values
    (1, 12.99, 5.00, 17.99, 'CREATED',
     'bbbbbbb1-bbbb-bbbb-bbbb-bbbbbbbbbbb1', 'aaaaaaa1-aaaa-aaaa-aaaa-aaaaaaaaaaa1', 1,
     1, '90001', '123 Main St',
     '42', 'Apt 101', 'Downtown',
     now(), null),

    (2, 21.98, 4.00, 25.98, 'CONFIRMED',
     'bbbbbbb2-bbbb-bbbb-bbbb-bbbbbbbbbbb2', 'aaaaaaa2-aaaa-aaaa-aaaa-aaaaaaaaaaa2', 2,
     2, '77001', '456 Oak Ave',
     '78', null, 'Westside',
     now() - interval '2 days', now() - interval '1 day');

insert into order_items (id, quantity, unit_price, total_price, observations, order_id, product_id)
values
    (1, 1, 12.99, 12.99, 'Extra cheese please', 1, 1),
    (2, 1, 10.99, 10.99, null, 2, 2),
    (3, 1, 10.99, 10.99, 'No peanuts', 2, 2);

insert into orders (id, subtotal, delivery_fee, total_value, status,
                    restaurant_id, user_id, payment_method_id,
                    address_city_id, address_zipcode, address_street,
                    address_number, address_neighborhood, date_created)
values
    (3, 20.00, 5.00, 25.00, 'CREATED',
     'bbbbbbb1-bbbb-bbbb-bbbb-bbbbbbbbbbb1', 'aaaaaaa1-aaaa-aaaa-aaaa-aaaaaaaaaaa1', 1,
     1, '90001', 'St A', '1', 'Downtown', now() - interval '8 days'),
    (4, 15.00, 4.00, 19.00, 'CONFIRMED',
     'bbbbbbb2-bbbb-bbbb-bbbb-bbbbbbbbbbb2', 'aaaaaaa2-aaaa-aaaa-aaaa-aaaaaaaaaaa2', 2,
     2, '77001', 'St B', '2', 'Chinatown', now() - interval '1 day'),
    (5, 12.00, 3.00, 15.00, 'DELIVERED',
     'bbbbbbb3-bbbb-bbbb-bbbb-bbbbbbbbbbb3', 'aaaaaaa3-aaaa-aaaa-aaaa-aaaaaaaaaaa3', 3,
     3, '10001', 'St C', '3', 'South Side', now() - interval '30 days');

-- Push each sequence above the highest existing id (avoids collisions with explicit test ids)
SELECT setval('states_id_seq', (SELECT MAX(id) FROM states));
SELECT setval('cities_id_seq', (SELECT MAX(id) FROM cities));
SELECT setval('payment_methods_id_seq', (SELECT MAX(id) FROM payment_methods));
SELECT setval('groups_id_seq', (SELECT MAX(id) FROM groups));
SELECT setval('permissions_id_seq', (SELECT MAX(id) FROM permissions));
SELECT setval('products_id_seq', (SELECT MAX(id) FROM products));
SELECT setval('orders_id_seq', (SELECT MAX(id) FROM orders));
SELECT setval('order_items_id_seq', (SELECT MAX(id) FROM order_items));
