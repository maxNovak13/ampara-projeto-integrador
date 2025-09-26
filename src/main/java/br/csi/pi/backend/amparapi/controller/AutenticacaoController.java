package br.csi.pi.backend.amparapi.controller;

import br.csi.pi.backend.amparapi.infra.security.TokenServiceJWT;
import br.csi.pi.backend.amparapi.model.profissional.Profissional;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class AutenticacaoController {
    private final AuthenticationManager manager;
    private final TokenServiceJWT tokenServiceJWT;

    public AutenticacaoController(AuthenticationManager manager, TokenServiceJWT tokenServiceJWT) {
        this.manager = manager;
        this.tokenServiceJWT = tokenServiceJWT;
    }

    @PostMapping()
    public ResponseEntity login(@RequestBody @Valid DadosAutenticacao dados) {
       try{
           UsernamePasswordAuthenticationToken authenticationToken =
                   new UsernamePasswordAuthenticationToken(dados.email(), dados.senha());

           Authentication at = manager.authenticate(authenticationToken);

           Profissional profissional = (Profissional) at.getPrincipal();
          // System.out.println("profissional infomado: " + profissional.getUuid() + " " + profissional.getNome());
           String token = this.tokenServiceJWT.gerarToken(profissional);

           return ResponseEntity.ok().body(new DadosTokenJWT(token));
       }
       catch(Exception e){
           e.printStackTrace();
           return ResponseEntity.badRequest().build();
       }
    }

    private record DadosTokenJWT (String token){}
    private record DadosAutenticacao(String email, String senha) { }
}