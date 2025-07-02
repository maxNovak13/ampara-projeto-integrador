package br.csi.pi.backend.amparapi.model.agressor;

import br.csi.pi.backend.amparapi.model.endereco.Endereco;
import br.csi.pi.backend.amparapi.model.registro.Registro;
import com.fasterxml.jackson.annotation.JsonBackReference;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Entity
@Table(name = "agressor")
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Entidade que representa o agressor de uma mulher.")
public class Agressor {
    @UuidGenerator
    @Schema(description = "UUID único do agressor", example = "6effe31d-6eaa-4a15-8330-ff78588cb843")
    private UUID uuid;

    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY)
    @Schema(description = "ID do agressor", example = "1")
    private Long id;

    @Size(max = 100, message = "Nome deve ter no máximo 100 caracteres")
    @Schema(description = "Nome completo do agressor", example = "Jorel Silva Machado")
    private String nome;

    @Size(max = 50, message = "Apelido deve ter no máximo 50 caracteres")
    @Schema(description = "Apelido do agressor", example = "Baixinho")
    private String apelido;

    @Schema(description = "Telefone do agressor", example = "(11) 91234-5678")
    @Size(max = 15, message = "Telefone deve ter no máximo 15 caracteres")
    private String telefone;

    @Schema(description = "CPF do agressor", example = "123.456.789-00")
    @Size(max = 14, message = "CPF deve ter no máximo 14 caracteres")
    private String cpf;

    @Schema(description = "Qualquer informação adicional sobre o agressor", example = "Estava de roupa preta, boné, tinha 1,80 e estava com uma faca")
    private String texto;

    @Embedded
    private Endereco endereco;

    //associação
    @Schema(description = "Registro de violência o qual está associado")
    @ManyToOne
    @JoinColumn(name = "registro_id", nullable = false)
    @JsonBackReference
    private Registro registro;

    public UUID getUuid() {
        return uuid;
    }

    public Long getId() {
        return id;
    }

    public @Size(max = 100, message = "Nome deve ter no máximo 100 caracteres") String getNome() {
        return nome;
    }

    public void setNome(@Size(max = 100, message = "Nome deve ter no máximo 100 caracteres") String nome) {
        this.nome = nome;
    }

    public @Size(max = 50, message = "Apelido deve ter no máximo 50 caracteres") String getApelido() {
        return apelido;
    }

    public void setApelido(@Size(max = 50, message = "Apelido deve ter no máximo 50 caracteres") String apelido) {
        this.apelido = apelido;
    }

    public @Size(max = 15, message = "Telefone deve ter no máximo 15 caracteres") String getTelefone() {
        return telefone;
    }

    public void setTelefone(@Size(max = 15, message = "Telefone deve ter no máximo 15 caracteres") String telefone) {
        this.telefone = telefone;
    }

    public @Size(max = 14, message = "CPF deve ter no máximo 14 caracteres") String getCpf() {
        return cpf;
    }

    public void setCpf(@Size(max = 14, message = "CPF deve ter no máximo 14 caracteres") String cpf) {
        this.cpf = cpf;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public @NotNull Registro getRegistro() {
        return registro;
    }

    public void setRegistro(@NotNull Registro registro) {
        this.registro = registro;
    }
}
