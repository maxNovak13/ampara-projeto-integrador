package br.csi.pi.backend.amparapi.model.endereco;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Classe que representa o endereço de uma entidade.")
public class Endereco {
    @Size(max = 100, message = "Deve ter menos de 100 digitos")
    @Schema(description = "Rua do endereço. Deve ser uma string e ter no máximo 100 caracteres.", example = "Rua Rio Branco")
    private String rua;
    @Size(max = 15, message = "Deve ter menos de 15 digitos")
    @Schema(description = "Número do endereço. Deve ser uma string e ter no máximo 15 caracteres", example = "1902")
    private String numero;
    @Size(max = 80, message = "Deve ter menos de 80 digitos")
    @Schema(description = "Bairro do endereço. Deve ser uma string e ter no máximo 80 caracteres.", example = "Centro")
    private String bairro;
    @Size(max = 100, message = "Deve ter menos de 100 digitos")
    @Schema(description = "Bairro do endereço. Deve ser uma string e ter no máximo 100 caracteres.", example = "Casa")
    private String complemento;

    public @Size(max = 100, message = "Deve ter menos de 100 digitos") String getRua() {
        return rua;
    }

    public void setRua(@Size(max = 100, message = "Deve ter menos de 100 digitos") String rua) {
        this.rua = StringUtils.capitalize(rua.toLowerCase());;
    }

    public @Size(max = 15, message = "Deve ter menos de 15 digitos") String getNumero() {
        return numero;
    }

    public void setNumero(@Size(max = 15, message = "Deve ter menos de 15 digitos") String numero) {
        this.numero = numero;
    }

    public @Size(max = 80, message = "Deve ter menos de 80 digitos") String getBairro() {
        return bairro;
    }

    public void setBairro(@Size(max = 80, message = "Deve ter menos de 80 digitos") String bairro) {
        this.bairro = StringUtils.capitalize(bairro.toLowerCase());
    }

    public @Size(max = 100, message = "Deve ter menos de 100 digitos") String getComplemento() {
        return complemento;
    }

    public void setComplemento(@Size(max = 100, message = "Deve ter menos de 100 digitos") String complemento) {
        this.complemento = complemento.toLowerCase();
    }
}