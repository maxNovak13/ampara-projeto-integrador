package br.csi.pi.backend.amparapi.controller;

import br.csi.pi.backend.amparapi.model.profissional.DadosProfissionalDTO;
import br.csi.pi.backend.amparapi.model.profissional.Profissional;
import br.csi.pi.backend.amparapi.service.AutenticacaoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class AutenticacaoController {///apenas autentica, sem lógica de token
    private final AutenticacaoService service;

    public AutenticacaoController(AutenticacaoService service) {
        this.service = service;
    }

    @PostMapping()
    public ResponseEntity<DadosProfissionalDTO> login(@RequestBody DadosAutenticacao dados) {
        Profissional profissional = service.autenticar(
                dados.email, dados.senha());
        return ResponseEntity.ok(new DadosProfissionalDTO(profissional));
    }

    public record DadosAutenticacao(String email, String senha) { }
}
