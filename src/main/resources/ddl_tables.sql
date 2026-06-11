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

alter table public.users
    owner to user39;

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

alter table public.security
    owner to user39;

create unique index security_user_id_uindex
    on public.security (user_id);

create table public.product
(
    id          serial
        constraint product_pk
            primary key,
    name        varchar(255) not null,
    description varchar(255),
    price       double precision,
    created     timestamp    not null,
    updated     timestamp    not null
);

alter table public.product
    owner to user39;

create table public.l_product_security
(
    id         serial
        constraint l_product_security_pk
            primary key,
    user_id    integer not null
        constraint l_product_security_users_id_fk
            references public.users
            on update cascade on delete cascade,
    product_id integer not null
        constraint l_product_security_product_id_fk
            references public.product
            on update cascade on delete cascade
);

alter table public.l_product_security
    owner to user39;

