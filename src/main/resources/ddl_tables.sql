create table public.users
(
    id         integer default nextval('user_id_seq'::regclass) not null
        constraint users_pk
            primary key,
    first_name varchar(50),
    last_name  varchar(50),
    age        integer,
    email      varchar(50),
    created    timestamp,
    updated    timestamp
);


create table public.security
(
    id       serial
        constraint security_pk
            primary key,
    user_id  integer      not null
        constraint security_user_id_fk
            references public.users
            on update cascade on delete cascade,
    username varchar(50)  not null,
    password varchar(200) not null,
    role     varchar(10)  not null,
    created  timestamp,
    updated  timestamp
);


