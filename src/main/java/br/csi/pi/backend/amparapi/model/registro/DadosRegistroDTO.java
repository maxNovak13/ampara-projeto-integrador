package br.csi.pi.backend.amparapi.model.registro;

import br.csi.pi.backend.amparapi.model.agressor.DadosAgressorDTO;

import java.util.List;

public record DadosRegistroDTO(Integer codigo, String nomeMulher, String creatAt, String dataOcorrido,
                               String depoimento, boolean violencia, String tipoViolencia,
                               String relacaoVitima, String lugar, String conduta,
                               String encaminhamento, String descricao,
                               String localAtendimento, String nomeProfissional,
                               List<DadosAgressorDTO> agressores) {
    public DadosRegistroDTO(Registro registro){
        this (
                registro.getCodigo(),
                registro.getMulher().getNome(),
                registro.getCreatedAt(),
                registro.getDataOcorrido(),
                registro.getDepoimento(),
                registro.isViolencia(),
                registro.getTipoViolencia(),
                registro.getRelacaoVitima(),
                registro.getLugar(),
                registro.getConduta(),
                registro.getEncaminhamento(),
                registro.getDescricao(),
                registro.getLocal().getNome(),
                registro.getProfissional().getNome(),
                registro.getAgressores() != null
                        ? registro.getAgressores().stream()
                        .map(DadosAgressorDTO::new)
                        .toList()
                        : List.of()
        );
    }
}
