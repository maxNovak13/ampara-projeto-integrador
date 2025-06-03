package br.csi.pi.backend.amparapi.controller;

import br.csi.pi.backend.amparapi.model.mulher.Mulher;
import br.csi.pi.backend.amparapi.model.profissional.DadosProfissionalDTO;
import br.csi.pi.backend.amparapi.model.profissional.DadosLoginDTO;
import br.csi.pi.backend.amparapi.model.profissional.Profissional;
import br.csi.pi.backend.amparapi.service.ProfissionalService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;


@RestController
@RequestMapping("/profissional")
public class ProfissionalController {
    private final ProfissionalService profissionalService;

    public ProfissionalController(ProfissionalService profissionalService) {
        this.profissionalService = profissionalService;
    }

    ///http://localhost:8080/ampara/profissional
    @PostMapping()
    @Transactional
    @Operation(summary = "Criar um novo profissional", description = "Cria um novo profissional e o adiciona à lista")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Profissional criado com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Profissional.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos", content = @Content)
    })
    public ResponseEntity<DadosProfissionalDTO> cadastrar(@RequestBody @Valid Profissional profissional, UriComponentsBuilder uriBuilder) {
        this.profissionalService.cadastrar(profissional);
        URI uri = uriBuilder.path("/uuid/{uuid}").buildAndExpand(profissional.getUuid()).toUri();
        DadosProfissionalDTO dto = new DadosProfissionalDTO(profissional);
        return ResponseEntity.created(uri).body(dto);
    }




    //nao será usado no front -> só para teste
    ///http://localhost:8080/ampara/profissional/{id} --> vai ser buscado
    @GetMapping("/{id}")
    @Operation(summary = "Busca profissional por ID", description = "Retorna um profissional correspondente ao ID fornecido")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Profissional encontrado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Profissional.class))),
            @ApiResponse(responseCode = "404", description = "Profissional não encontrado", content = @Content)
    })
    public DadosLoginDTO buscaProfissionalId(@Parameter(description = "ID do profissional a ser buscado") @PathVariable Long id) {
        return this.profissionalService.buscaProfissional(id);
    }

    ///http://localhost:8080/ampara/profissional/uuid/{uuid}
    @Operation(summary = "Obter profissional por UUID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Profissional encontrado",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation =  Profissional.class))}),
            @ApiResponse(responseCode = "404", description = "Profissional não encontrado")
    })
    @GetMapping("/uuid/{uuid}")
    public Profissional buscaProfissionalUuid(@PathVariable String uuid) {
        return this.profissionalService.buscaProfissionalUUID(uuid);
    }


    ///http://localhost:8080/ampara/profissional/listar
    @Operation(summary = "Listar todos os profissionais")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de profissionais obtida com sucesso",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = Profissional.class)))),
    })
    @GetMapping("/listar")
    public List<DadosProfissionalDTO> buscaTodos() {
        return this.profissionalService.listarProfissionais();
    }


    ///http://localhost:8080/ampara/profissional/{uuid}/situacao?nova=ATIVO
    @Operation(summary = "Atualiza a situação de um profissional", responses = {
                    @ApiResponse(responseCode = "204", description = "Situação atualizada com sucesso"),
                    @ApiResponse(responseCode = "404", description = "Profissional não encontrado"),
            }
    )
    @PatchMapping("/{uuid}/situacao")
    public ResponseEntity<Void> atualizarSituacao(@PathVariable String uuid, @RequestParam String nova) {//quando clicar no botao vai mandar o valor da nova situacao
        //futuramente adicionar lógica de verificar se é adm
        boolean atualizado = profissionalService.atualizarSituacao(uuid, nova);//futuramente adicionar lógica de verificar se é adm
        if (!atualizado) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }

    ///http://localhost:8080/ampara/profissional/{uuid}/boolean?adm=true
    @Operation(summary = "Dar ou tira acesso administrativo de um profissional", responses = {
            @ApiResponse(responseCode = "204", description = "Acesso atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Profissional não encontrado"),
        }
    )
    @PatchMapping("/{uuid}/boolean")//mudar
    public ResponseEntity<Void> mudarAcesso(@PathVariable String uuid, @RequestParam boolean adm) {//quando clicar no botao vai mandar o valor de adm
        boolean atualizado = profissionalService.mudarAcesso(uuid, adm);//futuramente adicionar lógica de verificar se é adm
        if (!atualizado) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}
