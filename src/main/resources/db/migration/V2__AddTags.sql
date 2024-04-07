create table osm.tags
(
    id             bigserial           not null
        constraint tags_seq primary key,
    uid            varchar(255) unique not null,
    created_at     timestamp           not null,
    last_update_at timestamp           not null,
    name           varchar(255) unique not null
);

create table osm.post_publications_tags
(
    post_publication_id bigint not null,
    tags_id              bigint not null,
    primary key (post_publication_id, tags_id),
    constraint post_publications_tags_FK_pp_id foreign key (post_publication_id) references osm.post_publications,
    constraint post_publications_tags_FK_tag_id foreign key (tags_id) references osm.tags
);