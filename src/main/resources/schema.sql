create table oauth_client_details (
  client_id VARCHAR(256) PRIMARY KEY,
  resource_ids VARCHAR(256),
  client_secret VARCHAR(256),
  scope VARCHAR(256),
  authorized_grant_types VARCHAR(256),
  web_server_redirect_uri VARCHAR(256),
  authorities VARCHAR(256),
  access_token_validity INTEGER,
  refresh_token_validity INTEGER,
  additional_information VARCHAR(4096),
  autoapprove VARCHAR(256)
);

create table oauth_client_token (
  token_id VARCHAR(256),
  token LONGVARBINARY,
  authentication_id VARCHAR(256) PRIMARY KEY,
  user_name VARCHAR(256),
  client_id VARCHAR(256)
);

create table oauth_access_token (
  token_id VARCHAR(256),
  token LONGVARBINARY,
  authentication_id VARCHAR(256) PRIMARY KEY,
  user_name VARCHAR(256),
  client_id VARCHAR(256),
  authentication LONGVARBINARY,
  refresh_token VARCHAR(256)
);

create table oauth_refresh_token (
  token_id VARCHAR(256),
  token LONGVARBINARY,
  authentication LONGVARBINARY
);

create table oauth_code (
  code VARCHAR(256), authentication LONGVARBINARY
);

create table oauth_approvals (
	userId VARCHAR(256),
	clientId VARCHAR(256),
	scope VARCHAR(256),
	status VARCHAR(10),
	expiresAt TIMESTAMP,
	lastModifiedAt TIMESTAMP
);

create table ClientDetails (
    appId VARCHAR(255) PRIMARY KEY,
    resourceIds VARCHAR(255),
    appSecret VARCHAR(255),
    scope VARCHAR(255),
    grantTypes VARCHAR(255),
    redirectUrl VARCHAR(255),
    authorities VARCHAR(255),
    access_token_validity INTEGER,
    refresh_token_validity INTEGER,
    additionalInformation VARCHAR(4096),
    autoApproveScopes VARCHAR(255)
);

create table user (
	id number(10) primary key auto_increment,
	name varchar(100),
	login varchar(100),
	password varchar(100),
	enabled boolean,
	unique (login)
);

create table role (
	id number(10) primary key auto_increment,
	name varchar(100)
);

create table user_role (
  	user_id number(10),
  	role_id number(10),
  	unique (user_id, role_id)
);


create table patient(
	prn number(10) primary key auto_increment,
	pidn number(10),
	name varchar(100),
	dob date,
	gender varchar(20),
	age number(5),
	blood_group varchar(10),
	contact varchar(20),
	marital_status varchar(10),
	address varchar(250),
	employment_status varchar(10),
	sole_bread_winner boolean,
	assets_owned varchar(150),
	photo_location varchar(256),
	bystander_name varchar(100),
	bystander_contact number(10),
	bystander_relation varchar(30) ,
	know_about_cancure varchar(100),
	recommendation_name varchar(100),
	recommendation_type varchar(20),
	doctor_name varchar(100),
	hospital varchar(100),
	diagnosis varchar(100),
	diagnosis_date date,
	doctor_comments varchar(250),
	type_of_support varchar(100)
);


create table support_organisations (
	org_id number(10) primary key auto_increment,
	name varchar(100),
	amount_rec number(10),
	prn number(10) references patient(prn)
);

create table patient_family (
	family_member_id number(10) primary key auto_increment,
	relation varchar(30),
	age number(5),
	status varchar(30),
	income number(10),
	other_income number(10),
	prn  number(10) references patient(prn)
);

create table patient_document (
	doc_id number(10) primary key auto_increment,
	doc_category varchar(50),
	doc_type varchar(100),
	doc_path varchar(250),
	prn number(10) references patient(prn)
);
