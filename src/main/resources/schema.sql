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
	email varchar(100),
	phone varchar(20),
	password varchar(100),
	enabled boolean,
	first_log boolean,
	unique (login)
);

create table role (
	id number(10) primary key auto_increment,
	name varchar(100),
	display_name varchar(100)
);

create table user_role (
  	user_id number(10),
  	role_id number(10),
  	unique (user_id, role_id)
);


 create table hospital (
 	hospital_id number(10) primary key auto_increment,
 	name varchar(100),
 	address varchar(200),
 	contact varchar(25),
 	enabled boolean
 );
 
  create table pharmacy (
 	pharmacy_id number(10) primary key auto_increment,
 	name varchar(100),
 	address varchar(200),
 	contact varchar(25),
 	enabled boolean
 );
 
  create table lab (
 	lab_id number(10) primary key auto_increment,
 	name varchar(100),
 	address varchar(200),
 	contact varchar(25),
 	enabled boolean
 );
 
 create table hpoc_hospital (
    hpoc_id number(10) primary key,
    hospital_id number(10),
    unique (hpoc_id, hospital_id)
 );
 
 create table ppoc_pharmacy (
    ppoc_id number(10) primary key,
    pharmacy_id number(10),
    unique (ppoc_id, pharmacy_id)
 );
 
 create table lpoc_lab (
    lpoc_id number(10) primary key,
    lab_id number(10),
    unique (lpoc_id, lab_id)
 );
 
 create table doctor (
 	doctor_id number(10) primary key auto_increment,
 	name varchar(100),
 	specification varchar(100),
 	address varchar(200),
 	contact varchar(25),
 	email varchar(50),
 	hospital_id number(10) references hospital(hospital_id),
 	user_id int(10) references user(id),
 	enabled boolean
 );

create table pidn_generator (
	prn number(10),
	pidn number(10) primary key auto_increment
);
 
create table patient(
	prn number(10) primary key auto_increment,
	pidn number(10),
	name varchar(100),
	dob date,
	gender varchar(20),
	blood_group varchar(10),
	contact varchar(20),
	marital_status varchar(10),
	address varchar(250),
	employment_status varchar(10),
	sole_bread_winner boolean,
	assets_owned varchar(150),
	photo_location varchar(256),
	bystander_name varchar(100),
	bystander_contact varchar(20),
	bystander_relation varchar(30) ,
	know_about_cancure varchar(100),
	recommendation_name varchar(100),
	recommendation_type varchar(20),
	doctor_name varchar(100),
	hospital varchar(100),
	diagnosis varchar(100),
	diagnosis_date date,
	doctor_comments varchar(2000),
	type_of_support varchar(100),
	task_id varchar(10),
	total_income number(10),
	aadhar_no varchar(12),
	preliminary_exam_doctor_id number(10) references DOCTOR(doctor_id),
	preliminary_exam_hospital_id number(10) references hospital(hospital_id),
	hospital_cost_estimate number(10),
	medical_cost_estimate number(10),
	hospital_cost_approved number(10),
	medical_cost_approved number(10),
	patient_type varchar(20),
	mbapproval_viewed_doctors varchar(50)
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
	task_id varchar(10),
	prn number(10) references patient(prn)
);

 create table investigator_type (
	name varchar(20) primary key
 );

create table patient_investigation (
	investigation_id number(10) primary key auto_increment,
	investigator_type varchar(100) references investigator_type(name),
 	investigator_id number(10),
 	investigation_date timestamp,
 	comments varchar(2000),
 	status varchar(30),
 	task_id varchar(10),
 	prn number(10) references patient(prn)
 );
 
 
 create table list (
 	list_id number(10) primary key auto_increment,
 	name varchar(100)
 );
  
 create table list_value (
    list_value_id number(10) primary key auto_increment,
    list_id number(10) references list(list_id), 
 	value varchar(100)
 );
 
 create table settings (
 	id number(10) primary key auto_increment,	
 	display_name varchar(100),
 	value varchar(2000)
 );
 
  
 create table account_types (
	id number(10) primary key auto_increment,
	name varchar(100)
 );

 create table journal(
	id number(10) primary key auto_increment,
	date date,
	from_account_type_id number(10) references account_types(id),
	from_account_holder_id number(10), 
	to_account_type_id number(10) references account_types(id),
	to_account_holder_id number(10),
	mode varchar(20),
	cheque_no varchar(30),
	comments varchar(200),
	amount decimal(10,2)
 );

 create table payment_workflow (
	id number(10) primary key auto_increment,
	status varchar(10),
	to_account_type_id number(10) references account_types(id),
	to_account_holder_id number(10)
);
 
 create table invoices (
	id number(10) primary key auto_increment,
	date date,
	pidn number(10) references pidn_generator(pidn),
	from_account_type_id number(10) references account_types(id),
	from_account_holder_id number(10), 
	to_account_type_id number(10) references account_types(id),
	to_account_holder_id number(10),
	amount decimal(10,2),
	status varchar(10),
	closed_date date,
	balance_amount number(10),
	partner_bill_no varchar(50),
	partner_bill_amount decimal(10,2),
	journal_id number(10) references journal(id),
	comments varchar(200)
 );
 
create table patient_visit (
	id number(10) primary key auto_increment,
	pidn number(10) references pidn_generator(pidn),
	date timestamp,
	account_type_id number(10) references account_types(id),
	account_holder_id number(10),
	task_id varchar(10),
	status varchar(10),
	topup_comments varchar(2000),
	topup_amount decimal(10,2)
 );

 create table patient_bills (
	bill_id number(10) primary key auto_increment,
	partner_bill_no varchar(50),
	partner_bill_amount decimal(10,2),
	partner_bill_path varchar(250),
	invoice_id number(10) references invoices(id),
	patient_visit_id number(10) references patient_visit(id)
);

 create table approvals (
	id number(10) primary key auto_increment,
	date timestamp,
	pidn number(10) references pidn_generator(pidn),
	amount decimal(10,2),
	approved_for_account_type_id number(10) references account_types(id),
	patient_visit_id number(10),
	expiry_date date
 );
 
create table patient_visit_forwards (
	id number(10) primary key auto_increment,
	pidn number(10) references pidn_generator(pidn),
	patient_visit_id number(10) references patient_visit(id),
	account_type_id number(10) references account_types(id),
	account_holder_id number(10),
	date timestamp,
	status varchar(10)
);

create table patient_visit_documents (
	doc_id number(10) primary key auto_increment,
	patient_visit_id number(10) references patient_visit(id),
	account_type_id number(10) references account_types(id),
	doc_type varchar(100),
	doc_path varchar(250)
 );

create table camp (
	camp_id number(10) primary key auto_increment,
	camp_name varchar(100),
	camp_place varchar(100) not null,
	camp_date date not null,
	medical_team varchar(100),
	local_poc_name varchar(100),
	local_poc_phone varchar(20),
	local_poc_email varchar(100),
	patient_count number(10) 
 );
 
create table camp_patient (
	camp_patient_id number(10) primary key auto_increment,
	uid varchar(100) not null,
	name varchar(100) not null,
	age number(3),
	gender varchar(20),
	phone varchar(20),
	camp_id number(10) references camp(camp_id)
 ); 

create index camp_patient_campid_fk_index
	on camp_patient(camp_id); 
 
create table camp_patient_test_results (
	test_result_id number(10) primary key auto_increment,
	test_result_text varchar(250),
	test_name varchar(100),
	test_result_path varchar(250),
	camp_patient_id number(10) references camp_patient(camp_patient_id)
 );

create index camp_patient_test_results_fk_index
	on camp_patient_test_results(camp_patient_id); 



CREATE TABLE payment_details (
  pay_id number(11) primary key AUTO_INCREMENT,
  transaction_id varchar(100),
  organisation varchar(100),
  designation varchar(100),
  product_name varchar(100),
  product_quantity int,
  product_amount decimal(11,2) NOT NULL,
  payer_fname varchar(100) NOT NULL,
  payer_lname varchar(200),
  payer_address varchar(200),
  message varchar(900),
  mobile varchar(100),
  telo varchar(100),
  telr varchar(100),
  payer_city varchar(100),
  tracking_id varchar(200),
  order_id varchar(200),
  order_status varchar(100),
  payment_mode varchar(100),
  status_message varchar(900),
  failure_message varchar(900),
  payer_state varchar(100),
  payer_zip varchar(100),
  payer_country varchar(200),
  payer_email varchar(100),
  status varchar(900),
  date timestamp
);
		
	
create table ACT_GE_PROPERTY (
    NAME_ varchar(64),
    VALUE_ varchar(300),
    REV_ integer,
    primary key (NAME_)
);


create table ACT_GE_BYTEARRAY (
    ID_ varchar(64),
    REV_ integer,
    NAME_ varchar(255),
    DEPLOYMENT_ID_ varchar(64),
    BYTES_ longvarbinary,
    GENERATED_ bit,
    primary key (ID_)
);

create table ACT_RE_DEPLOYMENT (
    ID_ varchar(64),
    NAME_ varchar(255),
    CATEGORY_ varchar(255),
    TENANT_ID_ varchar(255) default '',
    DEPLOY_TIME_ timestamp,
    primary key (ID_)
);

create table ACT_RE_MODEL (
    ID_ varchar(64) not null,
    REV_ integer,
    NAME_ varchar(255),
    KEY_ varchar(255),
    CATEGORY_ varchar(255),
    CREATE_TIME_ timestamp,
    LAST_UPDATE_TIME_ timestamp,
    VERSION_ integer,
    META_INFO_ varchar(4000),
    DEPLOYMENT_ID_ varchar(64),
    EDITOR_SOURCE_VALUE_ID_ varchar(64),
    EDITOR_SOURCE_EXTRA_VALUE_ID_ varchar(64),
    TENANT_ID_ varchar(255) default '',
    primary key (ID_)
);

create table ACT_RU_EXECUTION (
    ID_ varchar(64),
    REV_ integer,
    PROC_INST_ID_ varchar(64),
    BUSINESS_KEY_ varchar(255),
    PARENT_ID_ varchar(64),
    PROC_DEF_ID_ varchar(64),
    SUPER_EXEC_ varchar(64),
    ACT_ID_ varchar(255),
    IS_ACTIVE_ bit,
    IS_CONCURRENT_ bit,
    IS_SCOPE_ bit,
    IS_EVENT_SCOPE_ bit,
    SUSPENSION_STATE_ integer,
    CACHED_ENT_STATE_ integer,
    TENANT_ID_ varchar(255) default '',
    NAME_ varchar(255),
    LOCK_TIME_ timestamp,
    primary key (ID_)
);

create table ACT_RU_JOB (
    ID_ varchar(64) NOT NULL,
    REV_ integer,
    TYPE_ varchar(255) NOT NULL,
    LOCK_EXP_TIME_ timestamp,
    LOCK_OWNER_ varchar(255),
    EXCLUSIVE_ boolean,
    EXECUTION_ID_ varchar(64),
    PROCESS_INSTANCE_ID_ varchar(64),
    PROC_DEF_ID_ varchar(64),
    RETRIES_ integer,
    EXCEPTION_STACK_ID_ varchar(64),
    EXCEPTION_MSG_ varchar(4000),
    DUEDATE_ timestamp,
    REPEAT_ varchar(255),
    HANDLER_TYPE_ varchar(255),
    HANDLER_CFG_ varchar(4000),
    TENANT_ID_ varchar(255) default '',
    primary key (ID_)
);

create table ACT_RE_PROCDEF (
    ID_ varchar(64) NOT NULL,
    REV_ integer,
    CATEGORY_ varchar(255),
    NAME_ varchar(255),
    KEY_ varchar(255) NOT NULL,
    VERSION_ integer NOT NULL,
    DEPLOYMENT_ID_ varchar(64),
    RESOURCE_NAME_ varchar(4000),
    DGRM_RESOURCE_NAME_ varchar(4000),
    DESCRIPTION_ varchar(4000),
    HAS_START_FORM_KEY_ bit,
    HAS_GRAPHICAL_NOTATION_ bit,
    SUSPENSION_STATE_ integer,
    TENANT_ID_ varchar(255) default '',
    primary key (ID_)
);

create table ACT_RU_TASK (
    ID_ varchar(64),
    REV_ integer,
    EXECUTION_ID_ varchar(64),
    PROC_INST_ID_ varchar(64),
    PROC_DEF_ID_ varchar(64),
    NAME_ varchar(255),
    PARENT_TASK_ID_ varchar(64),
    DESCRIPTION_ varchar(4000),
    TASK_DEF_KEY_ varchar(255),
    OWNER_ varchar(255),
    ASSIGNEE_ varchar(255),
    DELEGATION_ varchar(64),
    PRIORITY_ integer,
    CREATE_TIME_ timestamp,
    DUE_DATE_ timestamp,
    CATEGORY_ varchar(255),
    SUSPENSION_STATE_ integer,
    TENANT_ID_ varchar(255) default '',
    FORM_KEY_ varchar(255),
    primary key (ID_)
);

create table ACT_RU_IDENTITYLINK (
    ID_ varchar(64),
    REV_ integer,
    GROUP_ID_ varchar(255),
    TYPE_ varchar(255),
    USER_ID_ varchar(255),
    TASK_ID_ varchar(64),
    PROC_INST_ID_ varchar(64) null,
    PROC_DEF_ID_ varchar(64),
    primary key (ID_)
);

create table ACT_RU_VARIABLE (
    ID_ varchar(64) not null,
    REV_ integer,
    TYPE_ varchar(255) not null,
    NAME_ varchar(255) not null,
    EXECUTION_ID_ varchar(64),
    PROC_INST_ID_ varchar(64),
    TASK_ID_ varchar(64),
    BYTEARRAY_ID_ varchar(64),
    DOUBLE_ double,
    LONG_ bigint,
    TEXT_ varchar(4000),
    TEXT2_ varchar(4000),
    primary key (ID_)
);

create table ACT_RU_EVENT_SUBSCR (
    ID_ varchar(64) not null,
    REV_ integer,
    EVENT_TYPE_ varchar(255) not null,
    EVENT_NAME_ varchar(255),
    EXECUTION_ID_ varchar(64),
    PROC_INST_ID_ varchar(64),
    ACTIVITY_ID_ varchar(64),
    CONFIGURATION_ varchar(255),
    CREATED_ timestamp not null,
    PROC_DEF_ID_ varchar(64),
    TENANT_ID_ varchar(255) default '',
    primary key (ID_)
);

create table ACT_EVT_LOG (
    LOG_NR_ identity,
    TYPE_ varchar(64),
    PROC_DEF_ID_ varchar(64),
    PROC_INST_ID_ varchar(64),
    EXECUTION_ID_ varchar(64),
    TASK_ID_ varchar(64),
    TIME_STAMP_ timestamp not null,
    USER_ID_ varchar(255),
    DATA_ longvarbinary,
    LOCK_OWNER_ varchar(255),
    LOCK_TIME_ timestamp,
    IS_PROCESSED_ bit default 0
);

create table ACT_PROCDEF_INFO (
	ID_ varchar(64) not null,
    PROC_DEF_ID_ varchar(64) not null,
    REV_ integer,
    INFO_JSON_ID_ varchar(64),
    primary key (ID_)
);

create index ACT_IDX_EXEC_BUSKEY on ACT_RU_EXECUTION(BUSINESS_KEY_);
create index ACT_IDX_TASK_CREATE on ACT_RU_TASK(CREATE_TIME_);
create index ACT_IDX_IDENT_LNK_USER on ACT_RU_IDENTITYLINK(USER_ID_);
create index ACT_IDX_IDENT_LNK_GROUP on ACT_RU_IDENTITYLINK(GROUP_ID_);
create index ACT_IDX_EVENT_SUBSCR_CONFIG_ on ACT_RU_EVENT_SUBSCR(CONFIGURATION_);
create index ACT_IDX_VARIABLE_TASK_ID on ACT_RU_VARIABLE(TASK_ID_);
create index ACT_IDX_ATHRZ_PROCEDEF on ACT_RU_IDENTITYLINK(PROC_DEF_ID_);
create index ACT_IDX_INFO_PROCDEF on ACT_PROCDEF_INFO(PROC_DEF_ID_);

alter table ACT_GE_BYTEARRAY
    add constraint ACT_FK_BYTEARR_DEPL
    foreign key (DEPLOYMENT_ID_)
    references ACT_RE_DEPLOYMENT;

alter table ACT_RE_PROCDEF
    add constraint ACT_UNIQ_PROCDEF
    unique (KEY_,VERSION_, TENANT_ID_);
    
alter table ACT_RU_EXECUTION
    add constraint ACT_FK_EXE_PROCINST
    foreign key (PROC_INST_ID_)
    references ACT_RU_EXECUTION;

alter table ACT_RU_EXECUTION
    add constraint ACT_FK_EXE_PARENT
    foreign key (PARENT_ID_)
    references ACT_RU_EXECUTION;
    
alter table ACT_RU_EXECUTION
    add constraint ACT_FK_EXE_SUPER 
    foreign key (SUPER_EXEC_) 
    references ACT_RU_EXECUTION;
    
alter table ACT_RU_EXECUTION
    add constraint ACT_FK_EXE_PROCDEF 
    foreign key (PROC_DEF_ID_) 
    references ACT_RE_PROCDEF (ID_);    
    
alter table ACT_RU_IDENTITYLINK
    add constraint ACT_FK_TSKASS_TASK
    foreign key (TASK_ID_)
    references ACT_RU_TASK;

alter table ACT_RU_IDENTITYLINK
    add constraint ACT_FK_ATHRZ_PROCEDEF
    foreign key (PROC_DEF_ID_)
    references ACT_RE_PROCDEF;
    
alter table ACT_RU_IDENTITYLINK
    add constraint ACT_FK_IDL_PROCINST
    foreign key (PROC_INST_ID_) 
    references ACT_RU_EXECUTION (ID_);       

alter table ACT_RU_TASK
    add constraint ACT_FK_TASK_EXE
    foreign key (EXECUTION_ID_)
    references ACT_RU_EXECUTION;

alter table ACT_RU_TASK
    add constraint ACT_FK_TASK_PROCINST
    foreign key (PROC_INST_ID_)
    references ACT_RU_EXECUTION;

alter table ACT_RU_TASK
  add constraint ACT_FK_TASK_PROCDEF
  foreign key (PROC_DEF_ID_)
  references ACT_RE_PROCDEF;

alter table ACT_RU_VARIABLE
    add constraint ACT_FK_VAR_EXE
    foreign key (EXECUTION_ID_)
    references ACT_RU_EXECUTION;

alter table ACT_RU_VARIABLE
    add constraint ACT_FK_VAR_PROCINST
    foreign key (PROC_INST_ID_)
    references ACT_RU_EXECUTION;

alter table ACT_RU_VARIABLE
    add constraint ACT_FK_VAR_BYTEARRAY
    foreign key (BYTEARRAY_ID_)
    references ACT_GE_BYTEARRAY;

alter table ACT_RU_JOB
    add constraint ACT_FK_JOB_EXCEPTION
    foreign key (EXCEPTION_STACK_ID_)
    references ACT_GE_BYTEARRAY;

alter table ACT_RU_EVENT_SUBSCR
    add constraint ACT_FK_EVENT_EXEC
    foreign key (EXECUTION_ID_)
    references ACT_RU_EXECUTION;

alter table ACT_RE_MODEL 
    add constraint ACT_FK_MODEL_SOURCE 
    foreign key (EDITOR_SOURCE_VALUE_ID_) 
    references ACT_GE_BYTEARRAY (ID_);

alter table ACT_RE_MODEL 
    add constraint ACT_FK_MODEL_SOURCE_EXTRA 
    foreign key (EDITOR_SOURCE_EXTRA_VALUE_ID_) 
    references ACT_GE_BYTEARRAY (ID_);
    
alter table ACT_RE_MODEL 
    add constraint ACT_FK_MODEL_DEPLOYMENT 
    foreign key (DEPLOYMENT_ID_) 
    references ACT_RE_DEPLOYMENT (ID_);
    
alter table ACT_PROCDEF_INFO 
    add constraint ACT_FK_INFO_JSON_BA 
    foreign key (INFO_JSON_ID_) 
    references ACT_GE_BYTEARRAY (ID_);

alter table ACT_PROCDEF_INFO 
    add constraint ACT_FK_INFO_PROCDEF 
    foreign key (PROC_DEF_ID_) 
    references ACT_RE_PROCDEF (ID_);
    
alter table ACT_PROCDEF_INFO
    add constraint ACT_UNIQ_INFO_PROCDEF
    unique (PROC_DEF_ID_);
    
create table ACT_HI_PROCINST (
    ID_ varchar(64) not null,
    PROC_INST_ID_ varchar(64) not null,
    BUSINESS_KEY_ varchar(255),
    PROC_DEF_ID_ varchar(64) not null,
    START_TIME_ timestamp not null,
    END_TIME_ timestamp,
    DURATION_ bigint,
    START_USER_ID_ varchar(255),
    START_ACT_ID_ varchar(255),
    END_ACT_ID_ varchar(255),
    SUPER_PROCESS_INSTANCE_ID_ varchar(64),
    DELETE_REASON_ varchar(4000),
    TENANT_ID_ varchar(255) default '',
    NAME_ varchar(255),
    primary key (ID_),
    unique (PROC_INST_ID_)
);

create table ACT_HI_ACTINST (
    ID_ varchar(64) not null,
    PROC_DEF_ID_ varchar(64) not null,
    PROC_INST_ID_ varchar(64) not null,
    EXECUTION_ID_ varchar(64) not null,
    ACT_ID_ varchar(255) not null,
    TASK_ID_ varchar(64),
    CALL_PROC_INST_ID_ varchar(64),
    ACT_NAME_ varchar(255),
    ACT_TYPE_ varchar(255) not null,
    ASSIGNEE_ varchar(255),
    START_TIME_ timestamp not null,
    END_TIME_ timestamp,
    DURATION_ bigint,
    TENANT_ID_ varchar(255) default '',
    primary key (ID_)
);

create table ACT_HI_TASKINST (
    ID_ varchar(64) not null,
    PROC_DEF_ID_ varchar(64),
    TASK_DEF_KEY_ varchar(255),
    PROC_INST_ID_ varchar(64),
    EXECUTION_ID_ varchar(64),
    NAME_ varchar(255),
    PARENT_TASK_ID_ varchar(64),
    DESCRIPTION_ varchar(4000),
    OWNER_ varchar(255),
    ASSIGNEE_ varchar(255),
    START_TIME_ timestamp not null,
    CLAIM_TIME_ timestamp,
    END_TIME_ timestamp,
    DURATION_ bigint,
    DELETE_REASON_ varchar(4000),
    PRIORITY_ integer,
    DUE_DATE_ timestamp,
    FORM_KEY_ varchar(255),
    CATEGORY_ varchar(255),
    TENANT_ID_ varchar(255) default '',
    primary key (ID_)
);

create table ACT_HI_VARINST (
    ID_ varchar(64) not null,
    PROC_INST_ID_ varchar(64),
    EXECUTION_ID_ varchar(64),
    TASK_ID_ varchar(64),
    NAME_ varchar(255) not null,
    VAR_TYPE_ varchar(100),
    REV_ integer,
    BYTEARRAY_ID_ varchar(64),
    DOUBLE_ double,
    LONG_ bigint,
    TEXT_ varchar(4000),
    TEXT2_ varchar(4000),
    CREATE_TIME_ timestamp,
    LAST_UPDATED_TIME_ timestamp,
    primary key (ID_)
);

create table ACT_HI_DETAIL (
    ID_ varchar(64) not null,
    TYPE_ varchar(255) not null,
    TIME_ timestamp not null,
    NAME_ varchar(255),
    PROC_INST_ID_ varchar(64),
    EXECUTION_ID_ varchar(64),
    TASK_ID_ varchar(64),
    ACT_INST_ID_ varchar(64),
    VAR_TYPE_ varchar(255),
    REV_ integer,
    BYTEARRAY_ID_ varchar(64),
    DOUBLE_ double,
    LONG_ bigint,
    TEXT_ varchar(4000),
    TEXT2_ varchar(4000),
    primary key (ID_)
);

create table ACT_HI_COMMENT (
    ID_ varchar(64) not null,
    TYPE_ varchar(255),
    TIME_ timestamp not null,
    USER_ID_ varchar(255),
    TASK_ID_ varchar(64),
    PROC_INST_ID_ varchar(64),
    ACTION_ varchar(255),
    MESSAGE_ varchar(4000),
    FULL_MSG_ longvarbinary,
    primary key (ID_)
);

create table ACT_HI_ATTACHMENT (
    ID_ varchar(64) not null,
    REV_ integer,
    USER_ID_ varchar(255),
    NAME_ varchar(255),
    DESCRIPTION_ varchar(4000),
    TYPE_ varchar(255),
    TASK_ID_ varchar(64),
    PROC_INST_ID_ varchar(64),
    URL_ varchar(4000),
    CONTENT_ID_ varchar(64),
    TIME_ timestamp,
    primary key (ID_)
);
create table ACT_HI_IDENTITYLINK (
    ID_ varchar(64),
    GROUP_ID_ varchar(255),
    TYPE_ varchar(255),
    USER_ID_ varchar(255),
    TASK_ID_ varchar(64),
    PROC_INST_ID_ varchar(64) null,
    primary key (ID_)
);

create index ACT_IDX_HI_PRO_INST_END on ACT_HI_PROCINST(END_TIME_);
create index ACT_IDX_HI_PRO_I_BUSKEY on ACT_HI_PROCINST(BUSINESS_KEY_);
create index ACT_IDX_HI_ACT_INST_START on ACT_HI_ACTINST(START_TIME_);
create index ACT_IDX_HI_ACT_INST_END on ACT_HI_ACTINST(END_TIME_);
create index ACT_IDX_HI_DETAIL_PROC_INST on ACT_HI_DETAIL(PROC_INST_ID_);
create index ACT_IDX_HI_DETAIL_ACT_INST on ACT_HI_DETAIL(ACT_INST_ID_);
create index ACT_IDX_HI_DETAIL_TIME on ACT_HI_DETAIL(TIME_);
create index ACT_IDX_HI_DETAIL_NAME on ACT_HI_DETAIL(NAME_);
create index ACT_IDX_HI_DETAIL_TASK_ID on ACT_HI_DETAIL(TASK_ID_);
create index ACT_IDX_HI_PROCVAR_PROC_INST on ACT_HI_VARINST(PROC_INST_ID_);
create index ACT_IDX_HI_PROCVAR_NAME_TYPE on ACT_HI_VARINST(NAME_, VAR_TYPE_);
create index ACT_IDX_HI_PROCVAR_TASK_ID on ACT_HI_VARINST(TASK_ID_);
create index ACT_IDX_HI_ACT_INST_PROCINST on ACT_HI_ACTINST(PROC_INST_ID_, ACT_ID_);
create index ACT_IDX_HI_IDENT_LNK_USER on ACT_HI_IDENTITYLINK(USER_ID_);
create index ACT_IDX_HI_IDENT_LNK_TASK on ACT_HI_IDENTITYLINK(TASK_ID_);
create index ACT_IDX_HI_IDENT_LNK_PROCINST on ACT_HI_IDENTITYLINK(PROC_INST_ID_);

create index ACT_IDX_HI_ACT_INST_EXEC on ACT_HI_ACTINST(EXECUTION_ID_, ACT_ID_);    
create index ACT_IDX_HI_TASK_INST_PROCINST on ACT_HI_TASKINST(PROC_INST_ID_);


create table ACT_ID_GROUP (
    ID_ varchar(64),
    REV_ integer,
    NAME_ varchar(255),
    TYPE_ varchar(255),
    primary key (ID_)
);

create table ACT_ID_MEMBERSHIP (
    USER_ID_ varchar(64),
    GROUP_ID_ varchar(64),
    primary key (USER_ID_, GROUP_ID_)
);

create table ACT_ID_USER (
    ID_ varchar(64),
    REV_ integer,
    FIRST_ varchar(255),
    LAST_ varchar(255),
    EMAIL_ varchar(255),
    PWD_ varchar(255),
    PICTURE_ID_ varchar(64),
    primary key (ID_)
);

create table ACT_ID_INFO (
    ID_ varchar(64),
    REV_ integer,
    USER_ID_ varchar(64),
    TYPE_ varchar(64),
    KEY_ varchar(255),
    VALUE_ varchar(255),
    PASSWORD_ longvarbinary,
    PARENT_ID_ varchar(255),
    primary key (ID_)
);

alter table ACT_ID_MEMBERSHIP
    add constraint ACT_FK_MEMB_GROUP
    foreign key (GROUP_ID_)
    references ACT_ID_GROUP;

alter table ACT_ID_MEMBERSHIP
    add constraint ACT_FK_MEMB_USER
    foreign key (USER_ID_)
    references ACT_ID_USER;

alter table user
    add column push_id varchar(256);
