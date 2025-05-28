create table states (
id serial primary key,
name varchar(80) not null
);

alter table cities add column state_id bigint not null;

alter table cities add constraint fk_cities_state
foreign key (state_id) references states (id);

alter table cities drop column name_state;

alter table cities rename column name_city to name;