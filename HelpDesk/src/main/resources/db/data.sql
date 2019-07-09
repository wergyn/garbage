INSERT INTO USER (first_name, last_name, role_id, email, password)
VALUES('Alexander', 'Koputerko', 'EMPLOYEE', 'user1_mogilev@yopmail.com', 'P@ssword1'),
      ('Timur', 'Borisov', 'EMPLOYEE', 'user2_mogilev@yopmail.com', 'P@ssword1'),

      ('Darya', 'Soloninko', 'MANAGER', 'manager1_mogilev@yopmail.com', 'P@ssword1'),
      ('Anton', 'Syman', 'MANAGER', 'manager2_mogilev@yopmail.com', 'P@ssword1'),

      ('Stanislav', 'Kolbasko', 'ENGINEER', 'engineer1_mogilev@yopmail.com', 'P@ssword1'),
      ('Alexander', 'Dovlyev', 'ENGINEER', 'engineer2_mogilev@yopmail.com', 'P@ssword1');

insert into CATEGORY(name)
values ('Hardware'),
       ('Software'),
       ('Services'),
       ('End user Support'),
       ('Customer Services');

insert into TICKET(name, description, category_id,created_on, desired_resolution_date,
owner_id, approver_id, assignee_id, urgency_id, state_id)
values ('task1', 'task1 description', 2, '2019-03-31', '2019-06-11', 2, null, null, 'CRITICAL', 'NEW'),
       ('task2', 'task2 description', 3, '2019-03-31', '2019-06-14', 1, null, null, 'HIGH', 'DRAFT'),
       ('task3', 'task3 description', 1, '2019-03-31', '2019-04-12', 1, null, null, 'AVERAGE', 'NEW'),
       ('task4', 'task4 description', 2, '2019-03-31', '2019-06-14', 1, 3, null, 'AVERAGE', 'APPROVED'),
       ('task5', 'task5 description', 5, '2019-03-31', '2019-05-14', 2, 3, null, 'LOW', 'CANCELED'),
       ('task6', 'task6 description', 3, '2019-03-31', '2019-06-13', 1, 3, null, 'AVERAGE', 'APPROVED'),
       ('task7', 'task7 description', 3, '2019-03-31', '2019-06-16', 1, 3, 5, 'CRITICAL', 'DONE'),
       ('task8', 'task8 description', 2, '2019-03-31', '2019-06-14', 1, null, null, 'AVERAGE', 'NEW'),
       ('task9', 'task9 description', 4, '2019-03-31', '2019-02-14', 1, 3, 5, 'HIGH', 'IN_PROGRESS'),
       ('task10', 'task10 description', 1, '2019-03-31', '2019-06-01', 2, null, null, 'AVERAGE', 'NEW'),
       ('task11', 'tas11 description', 3, '2019-03-31', '2019-04-18', 1, 3, null, 'LOW', 'DECLINED');




