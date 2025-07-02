package br.csi.pi.backend.amparapi.model.agressor;

import br.csi.pi.backend.amparapi.model.endereco.Endereco;
import java.util.UUID;

public record DadosAgressorDTO(UUID uuid, String nome, String apelido,
                               String telefone, String cpf, String texto,
                               Endereco endereco ) {
    public DadosAgressorDTO(Agressor agressor) {
        this(agressor.getUuid(),
                agressor.getNome(),
                agressor.getApelido(),
                agressor.getTelefone(),
                agressor.getCpf(),
                agressor.getTexto(),
                agressor.getEndereco()
        );
    }
}
