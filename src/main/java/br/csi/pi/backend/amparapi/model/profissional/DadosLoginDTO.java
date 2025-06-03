package br.csi.pi.backend.amparapi.model.profissional;

import java.util.UUID;

public record DadosLoginDTO(UUID uuid,
                            String email,
                            String senha) {
    public DadosLoginDTO(Profissional profissional){
        this(profissional.getUuid(),
                profissional.getEmail(),
                profissional.getSenha()
        );


    }
}
