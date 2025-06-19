alter table restaurants add column open boolean not null default true;
update restaurants set open = true;