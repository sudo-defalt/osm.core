create table osm.users
(
    id             bigserial           not null
        constraint users_seq primary key,
    uid            varchar(255) unique not null,
    created_at     timestamp           not null,
    last_update_at timestamp           not null,
    firstname      varchar(50)         not null,
    lastname       varchar(50)         not null,
    username       varchar(50) unique  not null,
    password       varchar(255)        not null,
    email          varchar(120) unique not null,
    phone_number   varchar(13) unique  not null
);

create table osm.followership
(
    id             bigserial           not null
        constraint followership_seq primary key,
    uid            varchar(255) unique not null,
    created_at     timestamp           not null,
    last_update_at timestamp           not null,
    follower_id    bigint              not null,
    followee_id    bigint              not null,
    status         smallint            not null,
    constraint followership_follower_fk foreign key (follower_id) references osm.users,
    constraint followership_followee_fk foreign key (followee_id) references osm.users
);

create table osm.post_publications
(
    id             bigserial           not null
        constraint post_publications_seq primary key,
    uid            varchar(255) unique not null,
    created_at     timestamp           not null,
    last_update_at timestamp           not null,
    publisher_id   bigint              not null,
    is_private     boolean             not null,
    likes          bigint              not null default 0,
    caption        varchar(300)        not null default '',
    constraint post_publications_publisher_fk foreign key (publisher_id) references osm.users
);

create table post_publication_files
(
    post_publication_id bigint       not null,
    files               varchar(255) not null,
    constraint post_publication_files_fk foreign key (post_publication_id) references osm.post_publications
);

create table osm.comments
(
    id             bigserial           not null
        constraint comments_seq primary key,
    uid            varchar(255) unique not null,
    created_at     timestamp           not null,
    last_update_at timestamp           not null,
    publisher_id   bigint              not null,
    is_private     boolean             not null,
    likes          bigint              not null default 0,
    message        varchar(300)        not null default '',
    post_id        bigint              not null,
    reply_id       bigint,
    constraint comments_publisher_fk foreign key (publisher_id) references osm.users,
    constraint comments_post_fk foreign key (post_id) references osm.post_publications,
    constraint comments_reply_fk foreign key (reply_id) references osm.comments
);