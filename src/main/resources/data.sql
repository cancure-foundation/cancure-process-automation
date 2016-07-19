insert into user(id, name, login, password, enabled) values (1,'Geetha','geetha','geetha', true);
insert into user(id, name, login, password, enabled) values (2,'cancure','cancure','cancure', true);
 
insert into role(id, name) values (1,'ROLE_ADMIN');
insert into role(id, name) values (2,'ROLE_GUEST');
insert into role(id, name) values (3,'ROLE_PROGRAM_COORDINATOR');
insert into role(id, name) values (4,'ROLE_HOSPITAL_POC');
insert into role(id, name) values (5,'ROLE_DOCTOR');
insert into role(id, name) values (6,'ROLE_SECRETARY');
insert into role(id, name) values (7,'ROLE_BOARD');

insert into user_role (user_id, role_id) values (1, 1);
insert into user_role (user_id, role_id) values (2, 1);

insert into patient values(1,1,'Ravi','2008-7-04','male',40,'A+','9898989898','married','kakkand','employed',true,'land','www','Raj',9999999999,'friend','news','Ram','MD','Piyush',
'sunrise','cancer','2008-7-05','none','financial');
insert into support_organisations values(1,'AON',10000,1);

insert into patient_family values(1,'father',60,'dependent',5000,100,1);
insert into patient_family values(2,'mother',56,'dependent',5000,100,1);

insert into patient_document values(1,'income','hello','hello',1);
insert into patient_document values(2,'id','license','/tmp/patient/license.jpg',1);
