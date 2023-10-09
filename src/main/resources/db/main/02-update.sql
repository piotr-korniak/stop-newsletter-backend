--liquibase formatted sql
--changeset piotrkorniak:2

ALTER TABLE SCENES
ADD NOTION UUID NULL;