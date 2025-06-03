package br.csi.pi.backend.amparapi.model.profissional;

import java.util.UUID;

public record DadosProfissionalDTO(UUID uuid, String nome, String profissao,
                                   String registro, String situacao, Boolean adm) {
    public DadosProfissionalDTO (Profissional profissional){
        this(profissional.getUuid(),
                profissional.getNome(),
                profissional.getProfissao(),
                profissional.getRegistro(),
                formatarSituacao(profissional.getSituacao()),
                profissional.isAdministrador());
    }

    private static String formatarSituacao(Profissional.Situacao situacao) {
        return switch (situacao) {
            case ATIVO -> "ATIVO";
            case INATIVO -> "INATIVO";
            case PENDENTE -> "PENDENTE";
        };
    }
}
