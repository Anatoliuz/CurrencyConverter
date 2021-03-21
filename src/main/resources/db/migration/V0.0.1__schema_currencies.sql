create schema currencies authorization test;

create table currencies.users
(
    id       bigserial primary key,
    username varchar(32)  not null unique,
    password varchar(256) not null,
    active   boolean
);

create table currencies.currencies
(
    id          bigserial primary key,
    external_id varchar(32) not null unique,
    num_code    int         not null unique,
    char_code   varchar(32) not null,
    nominal     real,
    name        varchar(64),
    value       real
);



create table currencies.history
(
    id      bigserial primary key,
    in_cur  bigint references currencies.currencies (id),
    out_cur bigint references currencies.currencies (id),
    in_sum  real,
    out_sum real,
    date    timestamp with time zone
);