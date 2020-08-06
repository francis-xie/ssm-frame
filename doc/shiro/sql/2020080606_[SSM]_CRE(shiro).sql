DROP DATABASE IF EXISTS shiro;
CREATE DATABASE shiro DEFAULT CHARACTER SET utf8;
USE shiro;

drop table if exists user;
drop table if exists role;
drop table if exists permission;
drop table if exists user_role;
drop table if exists role_permission;

create table user (
  id bigint auto_increment,
  name varchar(100),
  password varchar(100),
  salt varchar(100),
  constraint pk_users primary key(id)
) charset=utf8 ENGINE=InnoDB;

create table role (
  id bigint auto_increment,
  name varchar(100),
  desc_ varchar(100),
  constraint pk_roles primary key(id)
) charset=utf8 ENGINE=InnoDB;

create table permission (
  id bigint auto_increment,
  name varchar(100),
  desc_ varchar(100),
  url varchar(100),
  constraint pk_permissions primary key(id)
) charset=utf8 ENGINE=InnoDB;

create table user_role (
  id bigint auto_increment,
  uid bigint,
  rid bigint,
  constraint pk_users_roles primary key(id)
) charset=utf8 ENGINE=InnoDB;

create table role_permission (
  id bigint auto_increment,
  rid bigint,
  pid bigint,
  constraint pk_roles_permissions primary key(id)
) charset=utf8 ENGINE=InnoDB;
