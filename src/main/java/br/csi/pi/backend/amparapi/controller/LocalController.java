package br.csi.pi.backend.amparapi.controller;

import br.csi.pi.backend.amparapi.model.local.DadosLocalDTO;
import br.csi.pi.backend.amparapi.model.local.Local;
import br.csi.pi.backend.amparapi.model.mulher.Mulher;
import br.csi.pi.backend.amparapi.model.mulher.MulherResultadoBuscaDTO;
import br.csi.pi.backend.amparapi.service.LocalService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/local")
@Tag(name = "Locais", description = "Path relacionado a operações do local")
public class LocalController {
    private final LocalService localService;

    public LocalController(LocalService localService) { this.localService = localService; }

    ///http://localhost:8080/ampara/local
    @PostMapping
    @Transactional
    @Operation(summary = "Cria um novo local", description = "Cria um novo local e o adiciona à lista")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Local criado com sucesso",
            content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = Local.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos", content = @Content)

    })
    public ResponseEntity<HttpStatus> salvar(@RequestBody @Valid Local local) {
        this.localService.salvarLocal(local);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    ///http://localhost:8080/ampara/local/listar
    @Operation(summary = "Listar todos os locais", description = "Retorna uma lista de todos os locais da rede de apoio cadastrados.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de locais retornada com sucesso",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Local.class)))),
    })
    @GetMapping("/listar")
    public List<Local> listar() {
        return this.localService.listarLocais();
    }

    //http://localhost:8080/ampara/local/buscar?valor=delegacia
    @Operation(summary = "Buscar locais por nome",
            description = "Retorna uma lista de locais com base no valor informado.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista encontrada com sucesso",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = DadosLocalDTO.class)))),
            @ApiResponse(responseCode = "400", description = "Não foi encontrado"),
    })
    @GetMapping("/buscar")
    public ResponseEntity<List<DadosLocalDTO>> buscarPorFiltro(@RequestParam String nome) {
        List<DadosLocalDTO> locais = localService.buscarLocaisPorNome(nome);
        return ResponseEntity.ok(locais);
    }
}
