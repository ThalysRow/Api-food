create table orders (
id serial primary key,
subtotal numeric(10,2) not null,
delivery_fee numeric(10,2) not null,
total_value numeric(10,2) not null,
status varchar(20) not null,
restaurant_id uuid not null,
user_id uuid not null,
payment_method_id integer not null,
address_city_id integer not null,
address_zipcode varchar(20) not null,
address_street varchar(100) not null,
address_number varchar(20) not null,
address_complement varchar(100) null,
address_neighborhood varchar(100) not null,
date_created timestamp not null default now(),
date_confirmed timestamp null,
date_cancelled timestamp null,
date_delivered timestamp null,

constraint fk_order_address_city foreign key (address_city_id) references cities(id),
constraint fk_order_restaurant foreign key (restaurant_id) references restaurants(id),
constraint fk_order_user foreign key (user_id) references users(id),
constraint fk_order_payment_method foreign key (payment_method_id) references payment_methods(id)
);

create table order_items (
id serial primary key,
quantity integer not null,
unit_price numeric(10,2) not null,
total_price numeric(10,2) not null,
observations varchar(255) null,
order_id integer not null,
product_id integer not null,

constraint fk_order_item_order foreign key (order_id) references orders(id),
constraint fk_order_item_product foreign key (product_id) references products(id)
);
