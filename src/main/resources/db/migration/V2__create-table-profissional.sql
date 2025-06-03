create table profissional (
                              id serial not null primary key,
                              uuid uuid DEFAULT gen_random_uuid(),
                              nome varchar(100) not null,
                              profissao varchar(25) not null,
                              registro varchar(20) not null,
                              email varchar(150) not null unique,
                              senha varchar(80) not null,
                              situacao varchar(10) not null,
                              administrador boolean not null
    )
---CREATE EXTENSION pgcrypto;