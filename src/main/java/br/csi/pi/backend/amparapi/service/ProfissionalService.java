package br.csi.pi.backend.amparapi.service;

import br.csi.pi.backend.amparapi.model.local.DadosLocalDTO;
import br.csi.pi.backend.amparapi.model.profissional.*;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProfissionalService {

    private final ProfissionalRepository profissionalRepository;

    public ProfissionalService(ProfissionalRepository profissionalRepository) {
        this.profissionalRepository = profissionalRepository;
    }

    public void cadastrar(Profissional profissional) {//ao cadastrar seta como adm=user e situação=PENDENTE
        profissional.setSenha(new BCryptPasswordEncoder().encode(profissional.getSenha()));//criptografia
        profissional.setSituacao(Profissional.Situacao.PENDENTE);
        profissional.setRole(Profissional.Role.USER);
        this.profissionalRepository.save(profissional);
    }

    public List<DadosProfissionalDTO> listar() {
        return this.profissionalRepository.findAll(Sort.by("nome")) // ordena pelo campo nome
                .stream()
                .map(DadosProfissionalDTO::new)
                .toList();
    }

    public Profissional buscaProfissionalUUID(String uuid) {
        UUID uuidformatado = UUID.fromString(uuid);
        return this.profissionalRepository.findProfissionalByUuid(uuidformatado)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Profissional não encontrado"));
    }

    public boolean atualizarSituacao(String uuid, String novaSituacao) {
        UUID uuidFormatado = UUID.fromString(uuid);
        try {
            Profissional prof = profissionalRepository.findProfissionalByUuid(uuidFormatado)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Profissional não encontrado"));
            prof.setSituacao(Profissional.Situacao.valueOf(novaSituacao));
            profissionalRepository.save(prof);
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }

    public boolean mudarAcesso(String uuid, String mudarAdm) {
        UUID uuidformatado = UUID.fromString(uuid);
        Profissional prof = profissionalRepository.findProfissionalByUuid(uuidformatado)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Profissional não encontrado"));
            prof.setRole(Profissional.Role.valueOf(mudarAdm));
            profissionalRepository.save(prof);
            return true;
    }


}
