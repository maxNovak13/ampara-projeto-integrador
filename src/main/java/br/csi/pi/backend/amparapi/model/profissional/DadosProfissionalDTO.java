package br.csi.pi.backend.amparapi.model.profissional;

import java.util.UUID;

public record DadosProfissionalDTO(UUID uuid, String nome, String profissao,
                                   String registro, String situacao, String role) {
    public DadosProfissionalDTO (Profissional profissional){
        this(profissional.getUuid(),
                profissional.getNome(),
                profissional.getProfissao(),
                profissional.getRegistro(),
                profissional.getSituacao().name(),
                profissional.getRole().name());
    }

}
