# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table item_backlog (
  id                        bigint not null,
  produto_id                bigint,
  nome                      varchar(255),
  descricao                 varchar(255),
  notas                     varchar(255),
  importancia               integer,
  estimativa                integer,
  constraint pk_item_backlog primary key (id))
;

create table produto (
  id                        bigint not null,
  nome                      varchar(255),
  descricao                 varchar(255),
  constraint pk_produto primary key (id))
;

create sequence item_backlog_seq;

create sequence produto_seq;

alter table item_backlog add constraint fk_item_backlog_produto_1 foreign key (produto_id) references produto (id) on delete restrict on update restrict;
create index ix_item_backlog_produto_1 on item_backlog (produto_id);



# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists item_backlog;

drop table if exists produto;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists item_backlog_seq;

drop sequence if exists produto_seq;

