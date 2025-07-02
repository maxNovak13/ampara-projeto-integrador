package br.csi.pi.backend.amparapi.model.profissional;

import java.util.UUID;

public record DadosLoginDTO(UUID uuid,
                            String nome,
                            String situacao,
                            String role) {

    public DadosLoginDTO(Profissional profissional){
        this(   profissional.getUuid(),
                profissional.getNome(),
                profissional.getSituacao().name(),
                profissional.getRole().name()
        );
    }
}
