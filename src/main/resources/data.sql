insert into roles(insert_date_time, insert_user_id, is_deleted, last_update_date_time, last_update_user_id, description)
VALUES ('2021-01-05 00:00:00', 1, false, '2021-01-05 00:00:00', 1, 'Admin'),
       ('2021-01-05 00:00:00', 1, false, '2021-01-05 00:00:00', 1, 'Manager'),
       ('2021-01-05 00:00:00', 1, false, '2021-01-05 00:00:00', 1, 'Employee');

insert into users(insert_date_time, insert_user_id, is_deleted, last_update_date_time, last_update_user_id, enabled,
                  first_name, gender, last_name, username, role_id,password)
values ('2021-01-05 00:00:00', 1, false, '2021-01-05 00:00:00', 1, true, 'Admin', 'MALE', 'admin', 'admin@admin.com',
        1,'$2a$10$Q7ilQ6Hv11qpU0T7xfMzMeqxoPXkvhTVXxFqg0UL2xvLnhNqB7vba');
--
-- admin@admin.com: admin
-- waris0129@hotmail.com : waris
-- mike@hotmail.com : mike
--ginawaris@gmail.com: gina