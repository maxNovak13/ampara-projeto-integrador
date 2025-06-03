package br.csi.pi.backend.amparapi.service;

import br.csi.pi.backend.amparapi.model.profissional.Profissional;
import br.csi.pi.backend.amparapi.model.profissional.ProfissionalRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AutenticacaoService {//resolver autenticacao
    private final ProfissionalRepository profissionalRepository;
    private final PasswordEncoder passwordEncoder;

    public AutenticacaoService(ProfissionalRepository profissionalRepository, PasswordEncoder passwordEncoder) {
        this.profissionalRepository = profissionalRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Profissional autenticar(String email, String senha) {
        Profissional profissional = profissionalRepository.findByEmail(email).
                 orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Profissional não encontrado"));

        if (!passwordEncoder.matches(senha, profissional.getSenha())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Senha inválida");
        }
        if(profissional.getSituacao() == Profissional.Situacao.PENDENTE || profissional.getSituacao() == Profissional.Situacao.INATIVO){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Acesso negado, você ainda não recebeu autorização para acessar o sistema.");
        }
        return profissional;
    }
}
