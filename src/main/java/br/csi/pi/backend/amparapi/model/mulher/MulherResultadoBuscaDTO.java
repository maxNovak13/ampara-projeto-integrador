package br.csi.pi.backend.amparapi.model.mulher;

import java.time.LocalDate;
import java.util.UUID;

public record MulherResultadoBuscaDTO(UUID uuid, String nome, LocalDate dataNascimento,
                                      String cpf, String nomeMae) {
    public MulherResultadoBuscaDTO(Mulher mulher){
        this(mulher.getUuid(),
                mulher.getNome(),
                mulher.getDataNascimento(),
                mulher.getCpf(),
                mulher.getNomeMae());
    }
}
