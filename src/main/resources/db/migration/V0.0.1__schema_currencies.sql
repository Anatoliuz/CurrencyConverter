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
    name        varchar(64)
);

create table currencies.values
(
    id          bigserial primary key,
    currency_id bigserial references currencies.currencies (id),
    nominal     real,
    value       real,
    date        date
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

-- default user for tests
-- login: 1
-- password: 1
insert into currencies.users (username, password, active)
values (1, '$2y$12$nIYoE7HIeRr3CL31Baxw/uO7wfcVmgB5xdjy7aL545xhijiavU7uO', true)