package br.csi.pi.backend.amparapi.controller;

import br.csi.pi.backend.amparapi.model.profissional.DadosProfissionalDTO;
import br.csi.pi.backend.amparapi.model.profissional.DadosLoginDTO;
import br.csi.pi.backend.amparapi.model.profissional.Profissional;
import br.csi.pi.backend.amparapi.model.registro.DadosRegistroTabelaDTO;
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
import org.springframework.security.core.context.SecurityContextHolder;
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

    ///http://localhost:8080/ampara/profissional/cadastro
    @PostMapping
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

    ///http://localhost:8080/ampara/profissional/listar
    @Operation(summary = "Listar todos os profissionais")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Lista de profissionais retornada com sucesso",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = DadosProfissionalDTO.class)))
            )
    })
    @GetMapping("/listar")
    public List<DadosProfissionalDTO> listarProfissionais() {
        return this.profissionalService.listar();
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
    public DadosLoginDTO buscaProfissionalUuid(@PathVariable String uuid) {
        Profissional prof = this.profissionalService.buscaProfissionalUUID(uuid);
        return new DadosLoginDTO(prof);

    }


    @Operation(summary = "Obter profissional logado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Profissional encontrado",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation =  Profissional.class))}),
            @ApiResponse(responseCode = "404", description = "Profissional não encontrado")
    })
    ///http://localhost:8080/ampara/profissional/logado
    @GetMapping("/logado")
    public ResponseEntity<DadosProfissionalDTO> getLogado() {
        Profissional p = (Profissional) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseEntity.ok(new DadosProfissionalDTO(p));
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
    @PatchMapping("/{uuid}/role")//mudar
    public ResponseEntity<Void> mudarAcesso(@PathVariable String uuid, @RequestParam String novo) {//quando clicar no botao vai mandar o valor de adm
        boolean atualizado = profissionalService.mudarAcesso(uuid, novo);
        if (!atualizado) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}
