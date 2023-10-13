--liquibase formatted sql
--changeset piotrkorniak:2

CREATE TABLE CONTENTS (
    ID UUID,
    SOURCE_ID UUID,
    TYPE VARCHAR(2),
    DATE TIMESTAMP,
    NAME VARCHAR(192),
    ACTIVE BOOLEAN,

    PRIMARY KEY (ID)
);