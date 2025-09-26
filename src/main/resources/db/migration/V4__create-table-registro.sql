create table registro (
                        id serial not null primary key,
                        uuid uuid DEFAULT gen_random_uuid(),
                        codigo int not null,
                        created_at TIMESTAMP DEFAULT now(),

                        mulher_id int not null,
                        local_id int not null,
                        profissional_id int not null,

                        data_ocorrido date,
                        depoimento text,
                        violencia boolean not null,
                        tipo_violencia varchar(200) not null,
                        relacao_vitima varchar(50) not null,
                        lugar varchar(100) not null,

                        conduta text not null,
                        encaminhamento varchar(200),
                        descricao text,

                        constraint fk_mulher foreign key (mulher_id) references mulher(id),
                        constraint fk_local foreign key (local_id) references local(id),
                        constraint fk_profissional foreign key (profissional_id) references profissional(id)
)