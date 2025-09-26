package br.csi.pi.backend.amparapi.model.registro;

import br.csi.pi.backend.amparapi.model.agressor.Agressor;
import br.csi.pi.backend.amparapi.model.local.Local;
import br.csi.pi.backend.amparapi.model.mulher.Mulher;
import br.csi.pi.backend.amparapi.model.profissional.Profissional;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "registro")
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Entidade que representa o caso de violência que uma mulher sofreu.")
public class Registro {

    @UuidGenerator
    @Schema(description = "UUID único de registro", example = "6effe31d-6eaa-4a15-8330-ff78588cb843")
    private UUID uuid;

    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY)
    @Schema(description = "ID do registro", example = "1")
    private Long id;

    @NotNull
    @Schema(description = "Código identificador do caso", example = "1023")
    private Integer codigo;

    @Column(name = "created_at", columnDefinition = "TIMESTAMP DEFAULT now()")
    @Schema(description = "Data de criação do registro")
    private LocalDateTime createdAt;

    @Schema(description = "Data em que ocorreu o caso", example = "2025-06-07")
    private LocalDate dataOcorrido;

    @NotBlank
    @Schema(description = "Depoimento da vítima", example = "Foi ameaçada pelo ex-companheiro.")
    private String depoimento;

    @NotNull
    @Schema(description = "Indica se houve violência física")
    private boolean violencia;

    @NotBlank
    @Size(max = 200)
    @Column(name = "tipo_violencia", length = 200)
    @Schema(description = "Tipo de violência sofrida", example = "psicológica")
    private String tipoViolencia;

    @NotBlank
    @Size(max = 50)
    @Column(name = "relacao_vitima", length = 50)
    @Schema(description = "Relação do agressor com a vítima", example = "companheiro")
    private String relacaoVitima;

    @NotBlank
    @Size(max = 100)
    @Schema(description = "Lugar do ocorrido", example = "residência")
    private String lugar;

    @NotBlank
    @Schema(description = "Conduta do profissional", example = "Realizado acolhimento, avaliação inicial e orientação sobre a rede de apoio e medidas legais")
    private String conduta;

    @Size(max = 200)
    @Schema(description = "Encaminhamento dado a vítima sobre o caso", example = "Encaminhada à delegacia")
    private String encaminhamento;

    @NotBlank
    @Schema(description = "Descrição do profissional sobre a vítima", example = "Vítima relata agressão por companheiro, apresenta machucados e está emocionalmente abalada")
    private String descricao;

    //associações
    @NotNull
    @Schema(description = "Vítima do caso de violência")
    @ManyToOne
    @JoinColumn(name = "mulher_id", nullable = false)
    private Mulher mulher;

    @NotNull
    @Schema(description = "Local em que a vítima está sendo atendida")
    @ManyToOne
    @JoinColumn(name = "local_id", nullable = false)
    private Local local;

    @NotNull
    @Schema(description = "Profissional que atendeu a vítima e registrou o caso")
    @ManyToOne
    @JoinColumn(name = "profissional_id", nullable = false)
    private Profissional profissional;

    @Schema(description = "Agressor(es) do caso de violência")
    @OneToMany(mappedBy = "registro", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Agressor> agressores = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public UUID getUuid() {
        return uuid;
    }

    public @NotNull Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(@NotNull Integer codigo) {
        this.codigo = codigo;
    }

    public String getCreatedAt() {
        return this.createdAt.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        //return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getDataOcorrido() {
        return this.dataOcorrido.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        //return dataOcorrido;
    }

    public void setDataOcorrido(LocalDate dataOcorrido) {
        this.dataOcorrido = dataOcorrido;
    }

    public @NotBlank String getDepoimento() {
        return depoimento;
    }

    public void setDepoimento(@NotBlank String depoimento) {
        this.depoimento = depoimento;
    }

    @NotNull
    public boolean isViolencia() {
        return violencia;
    }

    public void setViolencia(@NotNull boolean violencia) {
        this.violencia = violencia;
    }

    public @NotBlank @Size(max = 50) String getTipoViolencia() {
        return tipoViolencia;
    }

    public void setTipoViolencia(@NotBlank @Size(max = 50) String tipoViolencia) {
        this.tipoViolencia = tipoViolencia;
    }

    public @NotBlank @Size(max = 50) String getRelacaoVitima() {
        return relacaoVitima;
    }

    public void setRelacaoVitima(@NotBlank @Size(max = 50) String relacaoVitima) {
        this.relacaoVitima = relacaoVitima;
    }

    public @NotBlank @Size(max = 30) String getLugar() {
        return lugar;
    }

    public void setLugar(@NotBlank @Size(max = 30) String lugar) {
        this.lugar = lugar;
    }

    public @NotBlank @Size(max = 100) String getConduta() {
        return conduta;
    }

    public void setConduta(@NotBlank @Size(max = 100) String conduta) {
        this.conduta = conduta;
    }

    public @Size(max = 50) String getEncaminhamento() {
        return encaminhamento;
    }

    public void setEncaminhamento(@Size(max = 50) String encaminhamento) {
        this.encaminhamento = encaminhamento;
    }

    public @NotBlank String getDescricao() {
        return descricao;
    }

    public void setDescricao(@NotBlank String descricao) {
        this.descricao = descricao;
    }

    public @NotNull Mulher getMulher() {
        return mulher;
    }

    public void setMulher(@NotNull Mulher mulher) {
        this.mulher = mulher;
    }

    public @NotNull Local getLocal() {
        return local;
    }

    public void setLocal(@NotNull Local local) {
        this.local = local;
    }

    public @NotNull Profissional getProfissional() {
        return profissional;
    }

    public void setProfissional(@NotNull Profissional profissional) {
        this.profissional = profissional;
    }

    public List<Agressor> getAgressores() {
        return agressores;
    }

    public void setAgressores(List<Agressor> agressores) {
        this.agressores = agressores;
    }

    @PrePersist
    public void prePersist() {
        if (this.createdAt == null) {
            this.createdAt = LocalDateTime.now();
        }
    }

}
