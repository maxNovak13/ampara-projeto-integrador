package br.csi.pi.backend.amparapi.model.mulher;

import br.csi.pi.backend.amparapi.model.endereco.Endereco;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "mulher")
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Entidade que representa a mulher vítima de violência.")
public class Mulher {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY)
    @Schema(description = "ID do profissional", example = "1")
    private Long id;

    @UuidGenerator
    @Schema(description = "UUID único do profissional", example = "6effe31d-6eaa-4a15-8330-ff78588cb843")
    private UUID uuid;

    @Size(max = 100, message = "Nome deve ter no máximo 100 caracteres")
    @Schema(description = "Nome completo da mulher", example = "Silvana Poleto")
    @NotBlank
    private String nome;

    @Schema(description = "Data de nascimento da mulher", example = "10-09-2000")
    @JsonFormat(pattern = "dd/MM/yyyy")
    @NotBlank
    private LocalDate dataNascimento;

    @Schema(description = "CPF da mulher", example = "123.456.789-00")
    @Size(max = 14, message = "CPF deve ter no máximo 14 caracteres")
    @NotBlank
    private String cpf;

    @Schema(description = "Telefone para contato", example = "(11) 91234-5678")
    @Size(max = 15, message = "Telefone deve ter no máximo 15 caracteres")
    @NotBlank
    private String telefone;

    @Schema(description = "Nome da mãe da mulher", example = "Maria de Lourdes")
    @Size(max = 100, message = "Nome da mãe deve ter no máximo 100 caracteres")
    @NotBlank
    private String nomeMae;

    @Schema(description = "Escolaridade da mulher", example = "Ensino médio completo")
    @Size(max = 30, message = "Escolaridade deve ter no máximo 30 caracteres")
    @NotBlank
    private String escolaridade;

    @Schema(description = "Profissão da mulher", example = "Costureira")
    @Size(max = 50, message = "Profissão deve ter no máximo 50 caracteres")
    @NotBlank
    private String profissao;

    @Schema(description = "Estado civil da mulher", example = "Solteira")
    @Size(max = 20, message = "Estado civil deve ter no máximo 20 caracteres")
    @NotBlank
    private String estadoCivil;

    @Embedded
    @NonNull
    private Endereco endereco;

    public Long getId() {
        return id;
    }

    public UUID getUuid() {
        return uuid;
    }

    public @Size(max = 100, message = "Nome deve ter no máximo 100 caracteres") @NotBlank String getNome() {
        return nome;
    }

    public void setNome(@Size(max = 100, message = "Nome deve ter no máximo 100 caracteres") @NotBlank String nome) {
        this.nome = nome.toUpperCase();
    }

    public @NotBlank LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(@NotBlank LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public @Size(max = 14, message = "CPF deve ter no máximo 14 caracteres") @NotBlank String getCpf() {
        return String.format("%s.%s.%s-%s",
                cpf.substring(0, 3), //parte 1
                cpf.substring(3, 6),    //parte 2
                cpf.substring(6, 9),    //parte 3
                cpf.substring(9)); //parte 4
    }

    public void setCpf(@Size(max = 14, message = "CPF deve ter no máximo 14 caracteres") @NotBlank String cpf) {
        this.cpf = cpf.replaceAll("\\D", "");//salvar apenas numeros
    }

    public @Size(max = 15, message = "Telefone deve ter no máximo 15 caracteres") @NotBlank String getTelefone() {
        return telefone;
//        return String.format("(%s) %s-%s",
//                telefone.substring(0, 2),     // DDD
//                telefone.substring(2, 7),     // parte 1 do núm
//                telefone.substring(7));       // parte 2 do núm
    }

    public void setTelefone(@Size(max = 15, message = "Telefone deve ter no máximo 15 caracteres") @NotBlank String telefone) {
        String numeros = telefone.replaceAll("\\D", ""); // remove tudo que não for número
        this.telefone = String.format("(%s) %s-%s",// deixar no formato: (12) 91234-5678
                numeros.substring(0, 2),     // DDD
                numeros.substring(2, 7),     // primeira parte
                numeros.substring(7));       // segunda parte
    }

    public @Size(max = 100, message = "Nome da mãe deve ter no máximo 100 caracteres") @NotBlank String getNomeMae() {
        return nomeMae;
    }

    public void setNomeMae(@Size(max = 100, message = "Nome da mãe deve ter no máximo 100 caracteres") @NotBlank String nomeMae) {
        this.nomeMae = nomeMae.toUpperCase();
    }

    public @Size(max = 30, message = "Escolaridade deve ter no máximo 30 caracteres") @NotBlank String getEscolaridade() {
        return escolaridade;
    }

    public void setEscolaridade(@Size(max = 30, message = "Escolaridade deve ter no máximo 30 caracteres") @NotBlank String escolaridade) {
        this.escolaridade = escolaridade;
    }

    public @Size(max = 50, message = "Profissão deve ter no máximo 50 caracteres") @NotBlank String getProfissao() {
        return profissao;
    }

    public void setProfissao(@Size(max = 50, message = "Profissão deve ter no máximo 50 caracteres") @NotBlank String profissao) {
        this.profissao = profissao;
    }

    public @Size(max = 20, message = "Estado civil deve ter no máximo 20 caracteres") @NotBlank String getEstadoCivil() {
        return estadoCivil;
    }

    public void setEstadoCivil(@Size(max = 20, message = "Estado civil deve ter no máximo 20 caracteres") @NotBlank String estadoCivil) {
        this.estadoCivil = estadoCivil;
    }

    public @NotBlank Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(@NotBlank @NonNull Endereco endereco) {
        this.endereco = endereco;
    }
}
