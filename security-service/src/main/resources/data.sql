INSERT INTO tbl_role (role, status) VALUES ('ROLE_ADMIN','CREATED');
INSERT INTO tbl_role (role, status) VALUES ('ROLE_USER','CREATED');

INSERT INTO `tbl_users` (USER_NAME, password, EMAIL, ENABLED, status, create_at) VALUES ('edumar111','$2a$10$J11dsG20VhVJJYFapTNbxOtFgWyBOgTFKdRrsYENFMFI4VmYSJMh.','edumar111@gmail.com',1,'CREATED','2018-09-05');

INSERT INTO `tbl_users` (USER_NAME, password, EMAIL, ENABLED, status, create_at) VALUES ('admin','$2a$10$J11dsG20VhVJJYFapTNbxOtFgWyBOgTFKdRrsYENFMFI4VmYSJMh.','edumar111@gmail.com',1,'CREATED','2018-09-05');

INSERT INTO `tbl_authorities` (user_id, role) VALUES ('edumar111','ROLE_USER');
INSERT INTO `tbl_authorities` (user_id, role) VALUES ('admin','ROLE_ADMIN');
INSERT INTO `tbl_authorities` (user_id, role) VALUES ('admin','ROLE_USER');