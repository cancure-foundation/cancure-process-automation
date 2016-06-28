create table Users (
  user_id int,
  user_name varchar(256),
  password varchar(256),
  enabled boolean
);

create table Role (
  user_id int,
  role varchar(256)
);

