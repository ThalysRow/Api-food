alter table group_users drop constraint if exists fk_group_users_users;
alter table group_users add constraint fk_group_users_users
    foreign key (user_id) references users (id) on delete cascade;

alter table restaurants drop constraint if exists fk_restaurants_kitchens;
alter table restaurants add constraint fk_restaurants_kitchens
    foreign key (kitchen_id) references kitchens (id) on delete cascade;

create index if not exists idx_restaurants on restaurants(id);
create index if not exists idx_users on users(id);
create index if not exists idx_kitchens on kitchens(id);