package br.csi.pi.backend.amparapi.model.mulher;

import br.csi.pi.backend.amparapi.model.endereco.Endereco;

import java.time.LocalDate;
import java.util.UUID;

public record DadosMulherDTO(UUID uuid, String nome, LocalDate dataNascimento,
                             String cpf, String telefone, String nomeMae,
                             String escolaridade, String profissao, String estadoCivil,
                             Endereco endereco) {

    public DadosMulherDTO(Mulher mulher){
        this(mulher.getUuid(),
                mulher.getNome(),
                mulher.getDataNascimento(),
                mulher.getCpf(),
                mulher.getTelefone(),
                mulher.getNomeMae(),
                mulher.getEscolaridade(),
                mulher.getProfissao(),
                mulher.getEstadoCivil(),
                mulher.getEndereco()
        );
    }
}
