create table kitchens (
id uuid not null default gen_random_uuid(),
name varchar(60) not null,
primary key(id)
);