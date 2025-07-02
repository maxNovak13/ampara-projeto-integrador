package br.csi.pi.backend.amparapi.model.registro;

import java.util.UUID;

public record DadosRegistroTabelaDTO(UUID uuid, Integer codigo, String creatAt, String localAtendimento,
                                     String nomeMulher, String nomeProfissional, String dataOcorrido) {

    public DadosRegistroTabelaDTO(Registro registro){
        this(   registro.getUuid(),
                registro.getCodigo(),
                registro.getCreatedAt(),
                registro.getLocal().getNome(),
                registro.getMulher().getNome(),
                registro.getProfissional().getNome(),
                registro.getDataOcorrido());
    }

}
