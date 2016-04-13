# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table admin (
  id                        integer not null,
  email                     varchar(255),
  pass                      varchar(255),
  constraint pk_admin primary key (id))
;

create table student (
  id                        bigint not null,
  name                      varchar(255),
  usertype                  integer,
  birthday                  timestamp,
  adress                    varchar(255),
  phone                     bigint,
  email                     varchar(255),
  pass                      varchar(255),
  joined_year               varchar(255),
  faculty                   varchar(255),
  constraint pk_student primary key (id))
;

create table teacher (
  id                        bigint not null,
  name                      varchar(255),
  usertype                  integer,
  work                      varchar(255),
  phone                     bigint,
  email                     varchar(255),
  pass                      varchar(255),
  constraint pk_teacher primary key (id))
;

create sequence admin_seq;

create sequence student_seq;

create sequence teacher_seq;




# --- !Downs

drop table if exists admin cascade;

drop table if exists student cascade;

drop table if exists teacher cascade;

drop sequence if exists admin_seq;

drop sequence if exists student_seq;

drop sequence if exists teacher_seq;

