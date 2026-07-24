create table photo_product
(
    product_id integer not null primary key,
    file_name   varchar(150) not null,
    description  varchar(80),
    content_type varchar(80)  not null,
    content_size int          not null,

    constraint fk_photo_product_product foreign key (product_id) references products (id)
)