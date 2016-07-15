--liquibase formatted sql

--changeset supermanheng21:ChangePasswordLength
ALTER TABLE spitter MODIFY password VARCHAR(60);;
--rollback SELECT * FROM Spitter LIMIT 1;