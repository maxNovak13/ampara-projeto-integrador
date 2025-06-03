create table local(
                      id serial not null primary key,
                      nome varchar(100) not null,
                      rua varchar(100),
                      numero varchar(15),
                      bairro varchar(80),
                      complemento varchar(100)
)