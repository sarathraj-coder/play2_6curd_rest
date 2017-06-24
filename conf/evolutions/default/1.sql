# --- !Ups

CREATE TABLE "USER"(
   "ID"  SERIAL NOT NULL PRIMARY KEY     NOT NULL,
   "NAME"  TEXT    NOT NULL,
   "ADDRESS"  TEXT,
   "DATE_OF_BIRTH"  integer,
   "JOINING_DATE" integer,
   "DESIGNATION"  TEXT);


# --- !Downs

drop table "USER";