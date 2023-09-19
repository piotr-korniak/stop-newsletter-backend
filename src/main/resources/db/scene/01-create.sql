--liquibase formatted sql
--changeset piotrkorniak:1

CREATE TABLE SOURCES (
    ID BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    TYPE VARCHAR(2),
    API VARCHAR(128),
    URL VARCHAR(48),
    NAME VARCHAR(64),

    PRIMARY KEY (ID)
);