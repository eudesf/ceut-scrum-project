# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table produto (
  id                        bigint not null,
  nome                      varchar(255),
  descricao                 varchar(255),
  constraint pk_produto primary key (id))
;

create sequence produto_seq;




# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists produto;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists produto_seq;

