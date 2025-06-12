alter table restaurants add column active boolean not null default true;
update restaurants set active = true;