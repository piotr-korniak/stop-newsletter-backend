--liquibase formatted sql
--changeset piotrkorniak:1

CREATE TABLE SOURCES (
    ID UUID,
    TYPE VARCHAR(2),
    UPDATE TIMESTAMP NULL,
    URL VARCHAR(48),
    NAME VARCHAR(64),
    ACTIVE BOOLEAN,

    PRIMARY KEY (ID)
);

