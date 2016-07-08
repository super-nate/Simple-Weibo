--liquibase formatted sql

--changeset supermanheng21:AddRecord9n10
INSERT INTO Spittle (message, created_at) VALUES ('test9', Now());
INSERT INTO Spittle (message, created_at) VALUES ('test10', Now());
--rollback SELECT * FROM Spitter LIMIT 1;