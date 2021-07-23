create table bid (
    id bigserial not null,
    bid int4,
    lot_id int8,
    user_id int8,
    zoned_date_time timestamp not null,
    primary key (id)
);

create table buyout (
    id bigserial not null,
    buyout int4,
    lot_id int8,
    user_id int8,
    zoned_date_time timestamp not null,
    primary key (id)
);

create table lot (
    id bigserial not null,
    archive boolean not null,
    buyout int4,
    buyout_time int4,
    min_bid int4,
    title varchar(255),
    primary key (id)
);

create table users (
    id bigserial not null,
    cash int4,
    primary key (id)
);