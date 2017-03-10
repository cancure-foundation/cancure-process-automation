--create user 'cancure'@'localhost' identified by 'cancure123';
--grant all privileges on cancure.* to 'cancure'@'localhost' with grant option;

insert into user values (1,'Cancure','cancure','admin@cancure.in.net','9048998963','$2a$10$G5Hv6YeTOfV7SKU2s9rHEO3ZRGB6KjeeQIZi44xCL.flTQZeHFTm2', true, false);
 
insert into role(id, name, display_name) values (1,'ROLE_ADMIN', 'Admin');
insert into role(id, name, display_name) values (2,'ROLE_GUEST', 'Guest');
insert into role(id, name, display_name) values (3,'ROLE_PROGRAM_COORDINATOR', 'Program Coordinator');
insert into role(id, name, display_name) values (4,'ROLE_HOSPITAL_POC', 'Hospital POC');
insert into role(id, name, display_name) values (5,'ROLE_DOCTOR', 'Doctor');
insert into role(id, name, display_name) values (6,'ROLE_SECRETARY', 'Secretary');
insert into role(id, name, display_name) values (7,'ROLE_EXECUTIVE_COMMITTEE', 'Executive Committee');
insert into role(id, name, display_name) values (8,'ROLE_PHARMACY', 'Pharmacy Representative');
insert into role(id, name, display_name) values (9,'ROLE_LAB', 'Lab Representative');

insert into investigator_type values ('Doctor');
insert into investigator_type values ('Program Coordinator');
insert into investigator_type values ('Secretary');
insert into investigator_type values ('Executive Committee');

insert into list values (1, 'FamilyRelation');
insert into list values (2, 'BloodGroups');
insert into list values (3, 'MaritalStatus');
insert into list values (4, 'EmploymentStatus');
insert into list values (5, 'KnowAboutCancure');
insert into list values (6, 'RecommendationType');
insert into list values (7, 'IDProof');
insert into list values (8, 'IncomeProof');
insert into list values (9, 'TypeOfSupportSought');

insert into list_value values (1, 1, 'Wife');
insert into list_value values (2, 1, 'Husband');
insert into list_value values (3, 1, 'Son');
insert into list_value values (4, 1, 'Daughter');
insert into list_value values (5, 1, 'Sister');
insert into list_value values (6, 1, 'Brother');
insert into list_value values (7, 1, 'Father');
insert into list_value values (8, 1, 'Mother');
insert into list_value values (9, 1, 'Friend');
insert into list_value values (10, 1, 'Well Wisher');

insert into list_value values (11, 2, 'O+');
insert into list_value values (12, 2, 'O-');
insert into list_value values (13, 2, 'A+');
insert into list_value values (14, 2, 'A-');
insert into list_value values (15, 2, 'B+');
insert into list_value values (16, 2, 'B-');
insert into list_value values (17, 2, 'AB+');
insert into list_value values (18, 2, 'AB-');

insert into list_value values (19, 3, 'Single');
insert into list_value values (20, 3, 'Married');
insert into list_value values (21, 3, 'Divorced');
insert into list_value values (22, 3, 'Widow');
insert into list_value values (23, 3, 'Widower');

insert into list_value values (24, 4, 'Employed');
insert into list_value values (25, 4, 'Dependent');

insert into list_value values (26, 5, 'Attending Doctor');
insert into list_value values (27, 5, 'Cancure Medical Board');
insert into list_value values (28, 5, 'Other Cancure Members');
insert into list_value values (29, 5, 'Advertisement');
insert into list_value values (30, 5, 'Awareness Programme');
insert into list_value values (31, 5, 'Screening Camp');
insert into list_value values (32, 5, 'Existing beneficiary');
insert into list_value values (33, 5, 'Elected Representative');
insert into list_value values (34, 5, 'Government Official');

insert into list_value values (35, 6, 'LSG');
insert into list_value values (36, 6, 'CCF MB');
insert into list_value values (37, 6, 'Medical Fraternity');
insert into list_value values (38, 6, 'Life Member');
insert into list_value values (39, 6, 'Other Member');
insert into list_value values (40, 6, 'VIP/SIP');

insert into list_value values (41, 7, 'Voter id');
insert into list_value values (42, 7, 'Ration card');
insert into list_value values (43, 7, 'Driving License');
insert into list_value values (44, 7, 'Aadhar card');
insert into list_value values (45, 7, 'Utility Bill with patients name');
insert into list_value values (46, 7, 'Passport');
insert into list_value values (47, 7, 'Birth certificate');
insert into list_value values (48, 7, 'Others');

insert into list_value values (49, 8, 'BPL card');
insert into list_value values (50, 8, 'Ration card');
insert into list_value values (51, 8, 'Certificate by the Elected Representative');
insert into list_value values (52, 8, 'Certificate from Village Office');
insert into list_value values (53, 8, 'Others');

insert into list_value values (54, 9, 'Financial support');
insert into list_value values (55, 9, 'Lab Tests');
insert into list_value values (56, 9, 'Treatment');
insert into list_value values (57, 9, 'Medicine');
insert into list_value values (58, 9, 'Others');


insert into OAUTH_CLIENT_DETAILS values('cancureapp', 'restservice', 'cancure123456', 'read,write', 'password,refresh_token', null, 'USER', null,null, '{}', null);

insert into user_role (user_id, role_id) values (1, 1);

insert into settings values (1, 'ID Card Title', 'CANCURE Foundation');
insert into settings values (2, 'ID Card Address Line 1', 'Regd. Office: 60/3285, Benrub, P. K. Devoor Road,');
insert into settings values (3, 'ID Card Address Line 2', 'Perumanoor, Cochin -15');
insert into settings values (4, 'ID Card Phone Numbers', '7025 00 33 33, 9846 031 667');
insert into settings values (5, 'ID Card website', 'www.cancure.in');
insert into settings values (6, 'ID Card Email', 'info@cancure.in');
insert into settings values (7, 'Preliminary Doctor Examination Timeout Duration (Hours)', 'PT24H');
insert into settings values (8, 'Background Check Timeout Duration (Hours)', 'PT24H');
insert into settings values (9, 'Medical Board Doctor Approval Timeout Duration (Hours)', 'PT24H');
insert into settings values (10, 'Secretary Approval Timeout Duration (Hours)', 'PT24H');
insert into settings values (11, 'Executive Committee Timeout Duration (Hours)', 'PT5H');
insert into settings values (12, 'Background Check Reminder Frequency (ISO 8601)', 'R/PT24H');
insert into settings values (13, 'MB Doctor Reminder Frequency (ISO 8601)', 'R/PT24H');
insert into settings values (14, 'Sectretary Approval Reminder Frequency (ISO 8601)', 'R/PT24H');
insert into settings values (15, 'PatientRegWorkflowNotification_email', '<div style="border : 2px solid #f4961c;"><div style="background-color: #f4961c;color: #fff;padding:8px 15px;font-weight:600;">Cancure Foundation</div><div style="padding:15px;color: #222d32;font-weight:500;">Hi, <br><br><b>The following task been assigned to you.</b> <br> <br><table border=1 style="border-collapse: collapse;"><tr><th style="padding:4px 8px;"> PRN</th><th style="padding:4px 8px;"> Patient Name</th><th style="padding:4px 8px;"> Task Name</th><th style="padding:4px 8px;"> Task Expiry</th></tr><tr><td style="padding:4px 8px;">${prn}</td><td style="padding:4px 8px;">${patName}</td><td style="padding:4px 8px;">${taskName}</td><td style="padding:4px 8px;">${date}</td></tr></table><br>Visit <a href="http://www.cancure.in.net/#/app/patientRegHistory/${prn}">${patName}</a> <br> <br><b>Thanks,</b> <br>Admin</div></div>');
insert into settings values (16, 'PatientRegWorkflowNotification_sms', 'The following task has been assigned to you. PRN : ${prn} (${patName}). Task Name : ${taskName}. View at - http://www.cancure.in.net/#/app/patientRegHistory/${prn}. Cancure');
insert into settings values (17, 'PatientHospitalVisitPartnerNotification_email', '<div style="border : 2px solid #f4961c;"><div style="background-color: #f4961c;color: #fff;padding:8px 15px;font-weight:600;">Cancure Foundation</div><div style="padding:15px;color: #222d32;font-weight:500;">Hi, <br><br><b>A patient has been referred to you.</b> <br> <br><table border=1 style="border-collapse: collapse;"><tr><th style="padding:4px 8px;"> PIDN</th><th style="padding:4px 8px;"> Patient Name</th></tr><tr><td style="padding:4px 8px;">${pidn} </td><td style="padding:4px 8px;">${patName}</td></tr></table><br>Visit <a href="www.cancure.in.net">www.cancure.in.net</a> <br> <br><b>Thanks,</b> <br>Admin</div></div>');
insert into settings values (18, 'PatientHospitalVisitPartnerNotification_sms', 'A patient has been referred to you. PIDN : ${pidn} (${patName}). Cancure'); 
insert into settings values (19, 'PatientHospitalVisitSecretaryNotification_email', '<div style="border : 2px solid #f4961c;"> <div style="background-color: #f4961c;color: #fff;padding:8px 15px;font-weight:600;">Cancure Foundation</div><div style="padding:15px;color: #222d32;font-weight:500;">Hi, <br><br><b>You have received a request for approving additional amount to a patient.</b> <br> <br><table border=1 style="border-collapse: collapse;"><tr><th style="padding:4px 8px;"> PIDN</th><th style="padding:4px 8px;"> Patient Name</th></tr><tr><td style="padding:4px 8px;">${pidn} </td><td style="padding:4px 8px;">${patName} </td></tr></table><br>Visit <a href="www.cancure.in.net">www.cancure.in.net</a> <br> <br><b>Thanks,</b> <br>Admin</div></div>');
insert into settings values (20, 'PatientHospitalVisitSecretaryNotification_sms', 'You have received a request for approving additional amount to a patient. PIDN : ${pidn} (${patName}). Cancure'); 
insert into settings values (21, 'InvoiceSecretaryNotification_email','<div style="border : 2px solid #f4961c;"><div style="background-color: #f4961c;color: #fff;padding:8px 15px;font-weight:600;">Cancure Foundation</div><div style="padding:15px;color: #222d32;font-weight:500;">Hi, <br><br><b>An invoice has been generated by   ${accountHolderName}   to Cancure. </b> <br> <br><table border=1 style="border-collapse: collapse;"><tr><th style="padding:4px 8px;"> PIDN</th><th style="padding:4px 8px;"> Patient Name</th><th style="padding:4px 8px;"> Amount</th><th style="padding:4px 8px;"> Date</th></tr><tr><td style="padding:4px 8px;"> ${pidn} </td><td style="padding:4px 8px;"> ${patName} </td><td style="padding:4px 8px;"> ${amount} </td><td style="padding:4px 8px;"> ${date} </td></tr></table><br>Visit <a href="www.cancure.in.net">www.cancure.in.net</a> <br> <br><b>Thanks,</b> <br>Admin</div></div>'); 
insert into settings values (22, 'InvoiceSecretaryNotification_sms', 'An invoice has been generated by ${accountHolderName} to Cancure. PIDN : ${pidn} (${patName}). Amount : ${amount}. Date : ${date} Cancure');
insert into settings values (23, 'PatientRegWorkflowClosure_email','<div style="border : 2px solid #f4961c;"><div style="background-color: #f4961c;color: #fff;padding:8px 15px;font-weight:600;">Cancure Foundation</div><div style="padding:15px;color: #222d32;font-weight:500;">Hi, <br><br><b>The folowing workflow has been closed.</b> <br> <br><table border=1 style="border-collapse: collapse;"><tr><th style="padding:4px 8px;"> PRN</th><th style="padding:4px 8px;"> Patient Name</th></tr><tr><td style="padding:4px 8px;">${prn}</td><td style="padding:4px 8px;">${patName}</td></tr></table><br>Visit <a href="www.cancure.in.net/#/app/patientRegHistory/${prn}">www.cancure.in.net</a> <br> <br><b>Thanks,</b> <br>Admin</div></div>');
insert into settings values (24, 'PatientRegWorkflowClosure_sms','The patient registration workflow of ${patName} (PRN ${prn}) has been closed. View at - http://www.cancure.in.net/#/app/patientRegHistory/${prn}. Cancure');
insert into settings values (25, 'PatientRegWorkflowReminder_email', '<div style="border : 2px solid #f4961c;"><div style="background-color: #f4961c;color: #fff;padding:8px 15px;font-weight:600;">Cancure Foundation</div><div style="padding:15px;color: #222d32;font-weight:500;"> Hi, <br><br><b>The following task is pending in your queue.</b> <br> <br><table border=1 style="border-collapse: collapse;"><tr><th style="padding:4px 8px;"> PRN</th><th style="padding:4px 8px;"> Patient Name</th><th style="padding:4px 8px;"> Task Name</th><th style="padding:4px 8px;"> Task Expiry</th></tr><tr><td style="padding:4px 8px;">${prn}</td><td style="padding:4px 8px;">${patName}</td><td style="padding:4px 8px;">${taskName}</td><td style="padding:4px 8px;">${taskDueDate}</td></tr></table><br><i>Please take necessary action at your earliest convenience.</i> <br>Visit <a href="www.cancure.in.net/#/app/patientRegHistory/${prn}">www.cancure.in.net</a> <br> <br><b>Thanks,</b> <br>Cancure</div></div>');
insert into settings values (26, 'PatientRegWorkflowReminder_sms', 'The following task is pending in your queue. Pls take action. Patient : ${patName}. Visit - www.cancure.in.net/#/app/patientRegHistory/${prn}');
insert into settings values (27, 'PatientRegWorkflowDoctorView_email', '<div style="border : 2px solid #f4961c;"><div style="background-color: #f4961c;color: #fff;padding:8px 15px;font-weight:600;">Cancure Foundation</div><div style="padding:15px;color: #222d32;font-weight:500;"> Hi, <br><br><b>Doctor ${doctorName} is viewing the file of the below patient.</b> <br> <br><table border=1 style="border-collapse: collapse;"><tr><th style="padding:4px 8px;"> PRN</th><th style="padding:4px 8px;"> Patient Name</th></tr><tr><td style="padding:4px 8px;">${prn}</td><td style="padding:4px 8px;">${patName}</td></tr></table><br><br>You can view the file of that patient and enter your comments at <a href="www.cancure.in.net/#/app/patientRegHistory/${prn}">www.cancure.in.net</a> <br> <br><b>Thanks,</b> <br>Cancure</div></div>');
insert into settings values (28, 'PatientRegWorkflowDoctorView_sms', 'Doctor ${doctorName} is viewing the file of Patient : ${patName} (PRN: ${prn}). Cancure');

insert into account_types values (1, 'Cancure');
insert into account_types values (2, 'Patient');
insert into account_types values (3, 'Pharmacy');
insert into account_types values (4, 'Laboratory');
insert into account_types values (5, 'Hospital');

insert into ACT_GE_PROPERTY
values ('schema.version', '5.20.0.1', 1);

insert into ACT_GE_PROPERTY
values ('schema.history', 'create(5.20.0.1)', 1);

insert into ACT_GE_PROPERTY
values ('next.dbid', '1', 1);