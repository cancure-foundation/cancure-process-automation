insert into Users (user_id,user_name, password, enabled) values (1,'geetha', 'geetha', true);
insert into Users (user_id,user_name, password, enabled) values (2,'dantis', 'dantis', true);

insert into Role (user_id, role) values (1, 'Admin');
insert into Role (user_id, role) values (2, 'Doc');