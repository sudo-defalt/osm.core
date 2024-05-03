alter table osm.users
    add column access_method smallint not null default 1;

alter table osm.users
    alter access_method drop default;