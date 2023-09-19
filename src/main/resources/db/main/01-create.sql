--liquibase formatted sql
--changeset piotrkorniak:1

CREATE TABLE USERS (
   ID UUID,
   USERNAME VARCHAR(32),
   PASSWORD VARCHAR(64),

   PRIMARY KEY (ID)
);

CREATE TABLE SCENES (
   ID UUID,
   CODE VARCHAR(2),
   NAME VARCHAR(32),

   PRIMARY KEY (ID)
);

