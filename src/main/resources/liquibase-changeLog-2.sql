--liquibase formatted sql

--changeset supermanheng21:AddColumnToSpittle
ALTER TABLE Spittle ADD spitter_id int;
UPDATE Spittle SET spitter_id=1;
--rollback ALTER TABLE Spittle DROP COLUMN spitter_id;