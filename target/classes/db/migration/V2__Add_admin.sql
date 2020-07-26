insert into usrs (id, active, firstname, lastname, username, password, photo_link)
    values(1, true, 'admin', 'admin', 'admin', '123', 'fghj');

insert into user_role (user_id, roles)
    values (1, 'ADMIN'), (1, 'USER');