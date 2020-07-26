create sequence hibernate_sequence start 1 increment 1;
create table post (
    id int8 not null,
    anons varchar(255),
    full_text varchar(255),
    link varchar(255),
    title varchar(255),
    views int4 not null,
    primary key (id)
);
create table post_author (
    user_id int8,
    post_id int8 not null,
    primary key (post_id)
    );
create table user_posts (
    user_id int8 not null,
    post_id int8 not null
    );
create table user_repuest_posts (
    user_id int8 not null,
    post_id int8 not null
    );
create table user_role (
    user_id int8 not null,
    roles varchar(255)
    );
create table usrs (
    id int8 not null,
    active boolean not null,
    firstname varchar(255),
    lastname varchar(255),
    password varchar(255),
    photo_link varchar(255),
    username varchar(255),
    primary key (id)
    );
create table videos (
    id int8 not null,
    link varchar(255),
    name varchar(255),
    post_id int8,
    primary key (id)
    );
alter table if exists post_author
    add constraint FKpyot7e48hgcqr0q3baelydt2g
    foreign key (user_id) references usrs;
alter table if exists post_author
    add constraint FKd94154654a8nkvieu89w7t6ok
    foreign key (post_id) references post;
alter table if exists user_posts
    add constraint FKlolx69xyvt6592rlyialxdqkk
    foreign key (post_id) references post;
alter table if exists user_posts
    add constraint FKp074mw6w5prkwqsiodosc8fut
    foreign key (user_id) references usrs;
alter table if exists user_repuest_posts
    add constraint FKqtnamxrsg6ncafdnklxq1ua1q
    foreign key (post_id) references post;
alter table if exists user_repuest_posts
    add constraint FKhith7rr40565cnerv9t1bje0m
    foreign key (user_id) references usrs;
alter table if exists user_role
    add constraint FKj6q10q8158i3jfoeilrjlee6k
    foreign key (user_id) references usrs;
alter table if exists videos
    add constraint FK7qhi6205kydb9sjalg0x59ydj
    foreign key (post_id) references post;