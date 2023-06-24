-- Create database & user:
-- > sudo su postgres
-- > psql

create database sensor;
create user sensor_owner with password '1234';
grant all privileges on database sensor to sensor_owner;

-- Create tables:

create table sensor(
    id int primary key generated always as identity,
    name varchar(100) not null unique
);

create table measurement(
    id int generated always as identity,
    value double precision not null,
    raining boolean not null,
    measurement_date_time timestamp not null,
    sensor varchar(100) references sensor(name)
);