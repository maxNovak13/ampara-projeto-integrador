package br.csi.pi.backend.amparapi.service;


import br.csi.pi.backend.amparapi.model.profissional.*;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;
import java.util.UUID;

@Service
public class ProfissionalService {

    private final ProfissionalRepository profissionalRepository;

    @Value("${ADMIN_EMAIL}")
    private String adminEmail;


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
                .filter(p -> !adminEmail.equalsIgnoreCase(p.getEmail())) // exclui o user "Administrador" da lista retornada
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

            // bloqueia a alteração na situacao do admin principal
            if (prof.getEmail().equalsIgnoreCase("admin@ampara.com")) {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Não é permitido alterar a situação do administrador principal");
            }

            prof.setSituacao(Profissional.Situacao.valueOf(novaSituacao));
            profissionalRepository.save(prof);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean mudarAcesso(String uuid, String mudarAdm) {
        UUID uuidformatado = UUID.fromString(uuid);
        Profissional prof = profissionalRepository.findProfissionalByUuid(uuidformatado)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Profissional não encontrado"));

        // bloqueia alteração no papel do admin principal
        if (prof.getEmail().equalsIgnoreCase("admin@ampara.com")) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Não é permitido alterar o acesso do administrador principal");
        }

        prof.setRole(Profissional.Role.valueOf(mudarAdm));
        profissionalRepository.save(prof);
        return true;
    }


}
