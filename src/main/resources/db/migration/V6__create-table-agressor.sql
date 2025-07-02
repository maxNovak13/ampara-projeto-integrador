create table agressor (
                          id serial not null primary key,

                          registro_id int not null,

                          uuid uuid default gen_random_uuid(),
                          nome varchar(100),
                          apelido varchar(50),
                          telefone varchar(20),
                          cpf varchar(14),
                          texto text,
                          rua varchar(100),
                          numero varchar(15),
                          bairro varchar(80),
                          complemento varchar(100),

                         constraint fk_registro foreign key (registro_id) references registro(id)
);
