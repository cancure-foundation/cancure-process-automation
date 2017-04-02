insert into hospital values (1, 'Lissie', 'Kaloor', '234234', true);
insert into hospital values (2, 'EMC', 'Palarivattom', '464325646',true);

insert into user values (2,'HPOC','lissiehpoc','lissie@cancure.in.net','9048998963', '$2a$10$G5Hv6YeTOfV7SKU2s9rHEO3ZRGB6KjeeQIZi44xCL.flTQZeHFTm2', true, false);
insert into hpoc_hospital values (2, 1);
insert into user_role values (2, 4);

insert into user values (3,'Dr. One','drone','one@cancure.in.net','9048998963','$2a$10$G5Hv6YeTOfV7SKU2s9rHEO3ZRGB6KjeeQIZi44xCL.flTQZeHFTm2', true, false);
insert into user values (4,'Dr. Two','drtwo','two@cancure.in.net','9048998963','$2a$10$G5Hv6YeTOfV7SKU2s9rHEO3ZRGB6KjeeQIZi44xCL.flTQZeHFTm2', true, false);
insert into user values (5,'Dr. Three','drthree','three@cancure.in.net','9048998963','$2a$10$G5Hv6YeTOfV7SKU2s9rHEO3ZRGB6KjeeQIZi44xCL.flTQZeHFTm2', true, false);

insert into doctor values (1, 'Dr.One', 'MBBS', 'Kerala', '123123123', 'doc@doc.com', 1, 3, true);
insert into doctor values (2, 'Dr.Two', 'MBBS', 'Kochi', '462643', 'two@doc.com', 1, 4, true);
insert into doctor values (3, 'Dr.Three', 'MBBS', 'Kochi', '462643', 'three@doc.com', 1, 5, true);
insert into user_role values (3, 5);
insert into user_role values (4, 5);
insert into user_role values (5, 5);

insert into pharmacy values(1, 'Soorya Pharma', 'Kochi', '234324', true);

insert into user values (6,'PPOC','ppoc','ppoc@cancure.in.net', '123123232', '$2a$10$G5Hv6YeTOfV7SKU2s9rHEO3ZRGB6KjeeQIZi44xCL.flTQZeHFTm2', true, false);
insert into user_role values (6, 8);
insert into ppoc_pharmacy values (6, 1);

insert into user values (7,'EC1','ec1','ec1@cancure.in.net', '123123232', '$2a$10$G5Hv6YeTOfV7SKU2s9rHEO3ZRGB6KjeeQIZi44xCL.flTQZeHFTm2', true, false);
insert into user values (8,'EC2','ec2','ec2@cancure.in.net', '123123232', '$2a$10$G5Hv6YeTOfV7SKU2s9rHEO3ZRGB6KjeeQIZi44xCL.flTQZeHFTm2', true, false);
insert into user_role values (7, 7);
insert into user_role values (8, 7);

insert into user values (9,'secretary','secretary','secretary@cancure.in.net', '123123232', '$2a$10$G5Hv6YeTOfV7SKU2s9rHEO3ZRGB6KjeeQIZi44xCL.flTQZeHFTm2', true, false);
insert into user_role values (9, 6);


insert into APPROVALS values (1, '2016-01-01 12:12:12', 2, 20000, 3, 1, null);

insert into patient values (1, 1, 'John Doe', '1992-01-01', 'MALE', 'A-', '123123123', 'SINGLE', 'Address', 'Employed', 'N', 'House', null, 'Friend', '342342342', 'Friend', 
null, null, null, 'Sample Doc', 'Cooerative', 'Diagnosis', '2015-01-01',  'Needs treatment', 'Treatment', null, 1000, '123123123123', 1, 1, 10000, 2000, 1000, 1000, 'inPatient');

insert into patient values (2, 2, 'Miachael Reisaj', '1992-01-01', 'MALE', 'A-', '123123123', 'SINGLE', 'Address', 'Employed', 'N', 'House', null, 'Friend', '342342342', 'Friend', 
null, null, null, 'Sample Doc', 'Cooerative', 'Diagnosis', '2015-01-01',  'Needs treatment', 'Treatment', null, 1000, '123123123123', 1, 1, 10000, 2000, 1000, 1000, 'inPatient');

insert into PIDN_GENERATOR values (1,1);
insert into PIDN_GENERATOR values (2,2);

insert into approvals values (1, '2016-01-01 12:12:12', 1, 20000, 3, 1, null);
insert into approvals values (2, '2016-01-01 12:12:12', 1, 3000, 5, 1, null);
insert into approvals values (3, '2016-01-01 12:12:12', 2, 20000, 3, 1, null);
insert into approvals values (4, '2016-01-01 12:12:12', 2, 3000, 5, 1, null);




