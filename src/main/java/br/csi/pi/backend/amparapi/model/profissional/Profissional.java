package br.csi.pi.backend.amparapi.model.profissional;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "profissional")
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Entidade que representa o profissional vinculado a uma unidade da rede de apoio às vítimas de violência contra a mulher.")
public class Profissional implements UserDetails {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY)
    @Schema(description = "ID do profissional", example = "1")
    private Long id;

    @UuidGenerator
    @Schema(description = "UUID único do profissional", example = "6effe31d-6eaa-4a15-8330-ff78588cb843")
    private UUID uuid;

    @Size(max = 100, message = "Nome deve ter no máximo 100 caracteres")
    @Schema(description = "Nome completo do usuário", example = "Maria da Silva")
    @NotBlank
    private String nome;

    @Size(max = 25, message = "Profissão deve ter no máximo 25 caracteres")
    @Schema(description = "Profissão do usuário", example = "Enfermeiro")
    @NotBlank
    private String profissao;

    @Size(max = 20, message = "Registro deve ter no máximo 20 digitos")
    @Schema(description = "Registro profissional", example = "CRM/SP 123456")
    @NotBlank
    private String registro;

    @Email(message = "Formato de e-mail inválido")
    @Size(max = 150, message = "E-mail deve ter no máximo 150 caracteres")
    @Schema(description = "Endereço de e-mail do profissional", example = "felipe@exemplo.com")
    @NotBlank
    private String email;

    @Size(min = 6, max = 30, message = "A senha deve ter no mínimo 6 e no máximo 30 caracteres.")
    @Schema(description = "Senha do profissional", example = "senha1234")
    @NotBlank
    private String senha;

    @Enumerated(EnumType.STRING)
    @Schema(description = "Situação do cadastro", example = "ATIVO")
    private Situacao situacao;

    @Enumerated(EnumType.STRING)
    @Schema(description = "Indica se o profissional tem perfil de administrador", example = "USER")
    private Role role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (this.role == null) return List.of();
        return List.of(new SimpleGrantedAuthority( this.role.name()));
    }

    @Override
    public String getPassword() {
        return this.senha;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    public enum Situacao {
        ATIVO, INATIVO, PENDENTE
    }

    public enum Role {
            USER,
            ADMIN
    }

    public Long getId() {
        return id;
    }

    public @Size(max = 100, message = "Nome deve ter no máximo 100 caracteres") String getNome() {
        return nome;
    }

    public void setNome(@Size(max = 100, message = "Nome deve ter no máximo 100 caracteres") String nome) {
        this.nome = nome.toUpperCase();
    }

    public @Size(max = 25, message = "Profissão deve ter no máximo 25 caracteres") String getProfissao() {
        return profissao;
    }

    public void setProfissao(@Size(max = 25, message = "Profissão deve ter no máximo 25 caracteres") String profissao) {
        this.profissao = profissao.toUpperCase();
    }

    public @Size(max = 20, message = "Nome deve ter no máximo 20 digitos") String getRegistro() {
        return registro;
    }

    public void setRegistro(@Size(max = 20, message = "Nome deve ter no máximo 20 digitos") String registro) {
        this.registro = registro.toUpperCase();
    }

    public @Email(message = "Formato de e-mail inválido") @Size(max = 150, message = "E-mail deve ter no máximo 150 caracteres") String getEmail() {
        return email;
    }

    public void setEmail(@Email(message = "Formato de e-mail inválido") @Size(max = 150, message = "E-mail deve ter no máximo 150 caracteres") String email) {
        this.email = email;
    }

    public @Size(min = 6, max = 30, message = "A senha deve ter no mínimo 6 e no máximo 50 caracteres.") String getSenha() {
        return senha;
    }

    public void setSenha(@Size(min = 6, max = 50, message = "A senha deve ter no mínimo 6 e no máximo 50 caracteres.") String senha) {
        this.senha = senha;
    }

    public Situacao getSituacao() {
        return situacao;
    }

    public void setSituacao(Situacao situacao) {
        this.situacao = situacao;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public UUID getUuid() {
        return uuid;
    }
}
