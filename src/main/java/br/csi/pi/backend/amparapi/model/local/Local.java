package br.csi.pi.backend.amparapi.model.local;

import br.csi.pi.backend.amparapi.model.endereco.Endereco;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity
@Table(name = "local")
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Entidade que representa um local da rede de apoio às vítimas de violência")
public class Local {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY)
    @Schema(description = "ID do local", example = "1")
    private Long id;

    @NonNull
    @Size(max = 100, message = "Nome deve ter no máximo 100 digitos")
    @Schema(description = "Nome do local", example = "Delegacia")
    private String nome;

    @Embedded
    @NonNull
    private Endereco endereco;

    public Long getId() {
        return id;
    }

    public @NotBlank @Size(max = 100, message = "Nome deve ter no máximo 100 digitos") String getNome() {
        return nome;
    }

    public void setNome(@NotBlank @Size(max = 100, message = "Nome deve ter no máximo 100 digitos") @NonNull String nome) {
        this.nome = nome.toUpperCase();
    }

    public @NotBlank Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(@NotBlank @NonNull Endereco endereco) {
        this.endereco = endereco;
    }
}
