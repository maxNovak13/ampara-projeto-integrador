package br.csi.pi.backend.amparapi.model.local;

public record DadosLocalDTO(Long id, String nome) {
    public DadosLocalDTO (Local local){
        this ( local.getId(),
                local.getNome());
    }
}
