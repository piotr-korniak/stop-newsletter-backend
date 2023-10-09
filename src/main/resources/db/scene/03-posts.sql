--liquibase formatted sql
--changeset piotrkorniak:3

CREATE TABLE POSTS (
    ID INT,
    CONTENT_ID UUID,

    PRIMARY KEY (CONTENT_ID),
    FOREIGN KEY (CONTENT_ID) REFERENCES CONTENTS(ID)

);