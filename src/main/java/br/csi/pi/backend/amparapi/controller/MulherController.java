package br.csi.pi.backend.amparapi.controller;

import br.csi.pi.backend.amparapi.model.mulher.DadosMulherDTO;
import br.csi.pi.backend.amparapi.model.mulher.Mulher;
import br.csi.pi.backend.amparapi.model.mulher.MulherResultadoBuscaDTO;
import br.csi.pi.backend.amparapi.service.MulherService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/mulher")
public class MulherController {
    private final MulherService mulherService;

    public MulherController(MulherService mulherService) {
        this.mulherService = mulherService;
    }

    ///http://localhost:8080/ampara/mulher
    @PostMapping()
    @Transactional
    @Operation(summary = "Criar uma nova mulher", description = "Cria uma nova mulher e o adiciona à lista")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Mulher criada com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Mulher.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos", content = @Content)
    })
    public ResponseEntity<DadosMulherDTO> cadastrarMulher(@RequestBody @Valid Mulher mulher, UriComponentsBuilder uriBuilder) {
        this.mulherService.cadastraMulher(mulher);
        URI uri = uriBuilder.path("/uuid/{uuid}").buildAndExpand(mulher.getUuid()).toUri();
        DadosMulherDTO dto = new DadosMulherDTO(mulher);
        return ResponseEntity.created(uri).body(dto);
    }

    ///http://localhost:8080/ampara/mulher/uuid/{uuid}
    @Operation(summary = "Obter mulher por UUID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Mulher encontrado",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation =  Mulher.class))}),
            @ApiResponse(responseCode = "404", description = "Mulher não encontrado")
    })
    @GetMapping("/uuid/{uuid}")
    public DadosMulherDTO buscaMulherUuid(@PathVariable String uuid) {
        Mulher mulher = this.mulherService.buscarPorUuid(uuid);
        return new DadosMulherDTO(mulher); // retorna DTO
    }

    //apenas para teste
    //http://localhost:8080/ampara/mulher/listar
    @Operation(summary = "Listar todas as mulheres")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de mulheres obtida com sucesso",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Mulher.class)))),
    })
    @GetMapping("/listar")
    public List<DadosMulherDTO> buscaTodas() {
        return this.mulherService.listarMulheres();
    }

    ///http://localhost:8080/ampara/mulher
    @Operation(summary = "Atualizar mulher", description = "Atualiza as informações de uma mulher.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "As informações foram atualizadas com sucesso"),
            @ApiResponse(responseCode = "404", description = "Mulher não encontrada")
    })
    @PutMapping("/{uuid}")
    public ResponseEntity<Mulher> atualizar(@RequestBody Mulher mulher, @PathVariable String uuid) {
        Mulher mulherAtt = this.mulherService.atualizarDadosMulher(mulher, uuid);
        return ResponseEntity.ok(mulherAtt);
    }

    //http://localhost:8080/ampara/mulher/buscar?filtro=nome&valor=amanda
    @Operation(summary = "Buscar mulheres por filtro",
            description = "Retorna uma lista de mulheres com base no filtro especificado: nome, nomeMae, cpf ou dataNascimento.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista encontrada com sucesso",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = MulherResultadoBuscaDTO.class)))),
            @ApiResponse(responseCode = "400", description = "Parâmetro de busca inválido"),
    })
    @GetMapping("/buscar")
    public ResponseEntity<List<MulherResultadoBuscaDTO>> buscarPorFiltro(
            @RequestParam String filtro, @RequestParam String valor) {
        List<MulherResultadoBuscaDTO> mulheres = mulherService.buscarPorFiltro(filtro, valor);
        return ResponseEntity.ok(mulheres);
    }

}
