package br.csi.pi.backend.amparapi.controller;

import br.csi.pi.backend.amparapi.model.agressor.Agressor;
import br.csi.pi.backend.amparapi.model.registro.Registro;
import br.csi.pi.backend.amparapi.service.AgressorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/agressor")
public class AgressorController {

    private final AgressorService agressorService;

    public AgressorController(AgressorService agressorService) {
        this.agressorService = agressorService;
    }


    //http://localhost:8080/ampara/agressor/buscar/{uuid}
    @Operation(summary = "Buscar agressore por registro",
            description = "Retorna uma lista de agressores por registro")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista encontrada com sucesso",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Registro.class)))),
            @ApiResponse(responseCode = "404", description = "Agressores não encontrados")
    })
    @GetMapping("/buscar/{uuid}")
    public Agressor buscarAgressorUuid(@PathVariable String uuid) {
        return this.agressorService.buscarPorUuid(uuid);
    }
}
