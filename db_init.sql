create database EXODUS_IAM;
create user 'testuser'@'%' identified by 'password';
grant all on EXODUS_IAM.* to 'testuser'@'%;
