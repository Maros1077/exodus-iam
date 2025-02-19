INSERT INTO EXODUS_IAM.application_seq (next_val) VALUES(1);
INSERT INTO EXODUS_IAM.auth_point_type_seq (next_val) VALUES(1);
INSERT INTO EXODUS_IAM.identity_auth_point_seq (next_val) VALUES(1);
INSERT INTO EXODUS_IAM.identity_seq (next_val) VALUES(1);
INSERT INTO EXODUS_IAM.identity_tag_seq (next_val) VALUES(1);
INSERT INTO EXODUS_IAM.tag_type_seq (next_val) VALUES(1);
INSERT INTO EXODUS_IAM.oidc_client_seq (next_val) VALUES(1);

INSERT INTO EXODUS_IAM.application (id, name) VALUES(1, 'exodus');
INSERT INTO EXODUS_IAM.tag_type (id, name) VALUES(1, 'IDID');
INSERT INTO EXODUS_IAM.tag_type (id, name) VALUES(2, 'NICK');
INSERT INTO EXODUS_IAM.tag_type (id, name) VALUES(3, 'EMAIL');
INSERT INTO EXODUS_IAM.auth_point_type (id, name, require_value) VALUES(1, 'PASSWORD', 1);
INSERT INTO EXODUS_IAM.auth_point_type (id, name, require_value) VALUES(2, 'OTP', 0);
INSERT INTO EXODUS_IAM.oidc_client (id, application_id, client_id, grant_type) VALUES(1, 1, 'exodus_app', 'bearer');
