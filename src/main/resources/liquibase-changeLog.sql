--liquibase formatted sql

--changeset supermanheng21:1
create table Spittle (
	id int NOT NULL auto_increment,
	message varchar(140) not null,
	created_at timestamp not null,
	latitude double,
	longitude double,
	primary key(id)
);
create table Spitter (
	id int NOT NULL auto_increment,
	username varchar(20) unique not null,
	password varchar(20) not null,
	first_name varchar(30) not null,
	last_name varchar(30) not null,
	email varchar(30) not null,
	primary key(id)
);
--rollback drop table Spittle;
--rollback drop table Spitter;


--changeset supermanheng21:2
INSERT INTO Spitter (username, password, first_name, last_name, email) VALUES ('supermanheng21', '263502', 'Zhiheng', 'Xu', 'zhihengxu@hotmail.com');
INSERT INTO Spitter (username, password, first_name, last_name, email) VALUES ('supermanheng22', '263502', 'Zhiheng', 'Xu', 'zhiheng.xu@aalto.fi');
INSERT INTO Spittle (message, created_at) VALUES ('test1', Now());
INSERT INTO Spittle (message, created_at) VALUES ('test2', Now());
INSERT INTO Spittle (message, created_at) VALUES ('test3', Now());
INSERT INTO Spittle (message, created_at) VALUES ('test4', Now());
INSERT INTO Spittle (message, created_at) VALUES ('test5', Now());
INSERT INTO Spittle (message, created_at) VALUES ('test6', Now());
INSERT INTO Spittle (message, created_at) VALUES ('test7', Now());
INSERT INTO Spittle (message, created_at) VALUES ('test8', Now());


--changeset supermanheng21:3
ALTER TABLE Spittle ADD spitter_id int;
UPDATE Spittle SET spitter_id=1;
--rollback ALTER TABLE Spittle DROP COLUMN spitter_id;