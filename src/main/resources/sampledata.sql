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

--insert into APPROVALS values (1, '2016-01-01 12:12:12', 2, 20000, 3, 1, null);
