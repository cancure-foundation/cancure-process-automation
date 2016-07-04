insert into user(id, name, login, password, enabled) values (1,'Geetha','geetha','geetha', true);
insert into user(id, name, login, password, enabled) values (2,'cancure','cancure','cancure', true);
 
insert into role(id, name) values (1,'ROLE_ADMIN');
insert into role(id, name) values (2,'ROLE_GUEST');
insert into role(id, name) values (3,'ROLE_PROGRAM_COORDINATOR');
insert into role(id, name) values (3,'ROLE_HOSPITAL_POC');
insert into role(id, name) values (3,'ROLE_DOCTOR');
insert into role(id, name) values (3,'ROLE_SECRETARY');
insert into role(id, name) values (3,'ROLE_BOARD');

insert into user_role (user_id, role_id) values (1, 1);
insert into user_role (user_id, role_id) values (2, 1);

