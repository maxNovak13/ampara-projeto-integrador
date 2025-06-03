create table mulher (
                              id serial not null primary key,
                              uuid uuid DEFAULT gen_random_uuid(),
                              nome varchar(100) not null,
                              data_nascimento date not null,
                              cpf varchar(14) not null unique,
                              telefone varchar(20) not null,
                              nome_mae varchar(100) not null,
                              escolaridade varchar(30) not null,
                              profissao varchar(50) not null,
                              estado_civil varchar(20) not null,
                              rua varchar(100),
                              numero varchar(15),
                              bairro varchar(80),
                              complemento varchar(100)
)