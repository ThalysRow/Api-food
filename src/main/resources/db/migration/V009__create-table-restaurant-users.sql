create table restaurant_users(
    user_id uuid not null,
    restaurant_id uuid not null,
    primary key (user_id, restaurant_id));

alter table if exists restaurant_users add constraint fk_restaurant_user
foreign key (user_id) references users on delete cascade;

alter table if exists restaurant_users add constraint fk_restaurant_user_restaurant
foreign key (restaurant_id) references restaurants on delete cascade;

create index idx_restaurant_users_user_id on restaurant_users(user_id);
create index idx_restaurant_users_restaurant_id on restaurant_users(restaurant_id);