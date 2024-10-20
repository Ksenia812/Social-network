--liquibase formatted sql

--preconditions onFail:MARK_RAN
--precondition-sql-check expectedResult:0 select count(1) from pg_tables where schemaname = 'network'
--comment: initialization

-- 20241007-01-init-schema
CREATE SCHEMA IF NOT EXISTS network;

-- 20210809-01-init-tables
create table if not exists network.user
(
    id         uuid primary key,
    name       varchar(45) not null,
    surname    varchar(45) not null,
    birth_date date        null,
    email      varchar(45) not null,
    constraint user_email_uindex unique (email)
);

create table if not exists network.credentials
(
    id       uuid primary key,
    login    varchar(45)                       not null,
    password varchar(45)                       not null,
    user_id  uuid references network.user (id) not null,
    constraint credentials_login_uindex unique (login)
);