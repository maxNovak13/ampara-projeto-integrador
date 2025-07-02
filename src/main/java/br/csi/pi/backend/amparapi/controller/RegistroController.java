package br.csi.pi.backend.amparapi.controller;

import br.csi.pi.backend.amparapi.model.mulher.DadosMulherDTO;
import br.csi.pi.backend.amparapi.model.registro.DadosRegistroDTO;
import br.csi.pi.backend.amparapi.model.registro.DadosRegistroTabelaDTO;
import br.csi.pi.backend.amparapi.model.registro.Registro;
import br.csi.pi.backend.amparapi.service.RegistroService;
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
import java.util.List;

@RestController
@RequestMapping("/registro")
public class RegistroController {
    private final RegistroService registroService;

    public RegistroController(RegistroService registroService) {
        this.registroService = registroService;
    }

    ///http://localhost:8080/ampara/registro
    @PostMapping()
    @Transactional
    @Operation(summary = "Criar um novo registro", description = "Cria um novo registro e o adiciona à lista")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Registro criado com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Registro.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos", content = @Content)
    })
    public ResponseEntity<DadosRegistroTabelaDTO> cadastrarRegistro(@RequestBody @Valid Registro registro, UriComponentsBuilder uriBuilder) {
        Registro registroSalvo = this.registroService.salvarRegistro(registro);
        URI uri = uriBuilder.path("/uuid/{uuid}").buildAndExpand(registroSalvo.getUuid()).toUri(); // Usa o UUID do salvo
        DadosRegistroTabelaDTO dto = new DadosRegistroTabelaDTO(registroSalvo);
        return ResponseEntity.created(uri).body(dto);
    }

    ///http://localhost:8080/ampara/registro/uuid/{uuid}
    @Operation(summary = "Obter registro por UUID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Registro encontrado",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation =  Registro.class))}),
            @ApiResponse(responseCode = "404", description = "Registro não encontrado")
    })
    @GetMapping("/buscar-reg/{uuid}")
    public DadosRegistroDTO buscaRegistroUuid(@PathVariable String uuid) {
        Registro registro = this.registroService.buscarPorUuid(uuid);
        return new DadosRegistroDTO(registro);
    }

    //http://localhost:8080/ampara/registro/buscar/{id}
    @Operation(summary = "Buscar registros por mulher",
            description = "Retorna uma lista de registros de uma mulher")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista encontrada com sucesso",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Registro.class)))),
            @ApiResponse(responseCode = "404", description = "Registros não encontrados")
    })
    @GetMapping("/buscar/{uuid}")
    public List<DadosRegistroTabelaDTO> buscarRegistrosPorMulher(@PathVariable String uuid) {
        return this.registroService.listarPorMulherDesc(uuid);
    }

}
