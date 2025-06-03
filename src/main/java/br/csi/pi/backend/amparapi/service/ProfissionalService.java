package br.csi.pi.backend.amparapi.service;

import br.csi.pi.backend.amparapi.model.profissional.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ProfissionalService {
    private final ProfissionalRepository profissionalRepository;

    public ProfissionalService(ProfissionalRepository profissionalRepository) {
        this.profissionalRepository = profissionalRepository;
    }

    public void cadastrar(Profissional profissional) {//ao cadastrar seta como adm=false e situação=PENDENTE
        profissional.setSenha(new BCryptPasswordEncoder().encode(profissional.getSenha()));//criptografia
        profissional.setSituacao(Profissional.Situacao.PENDENTE);
        profissional.setAdministrador(false);
        this.profissionalRepository.save(profissional);
    }

    public DadosLoginDTO buscaProfissional(Long id){
        Profissional profissional = this.profissionalRepository.getReferenceById(id);
        return new DadosLoginDTO(profissional);
    }//apagar depois de testar

    public Profissional buscaProfissionalUUID(String uuid) {
        UUID uuidformatado = UUID.fromString(uuid);
        return this.profissionalRepository.findProfissionalByUuid(uuidformatado);
    }

    public List<DadosProfissionalDTO> listarProfissionais(){
        return this.profissionalRepository.findAll().stream().map(DadosProfissionalDTO::new).toList();
    }


    public boolean atualizarSituacao(String uuid, String novaSituacao) {//melhorar logica pra quando nao encontrar profissional
        UUID uuidFormatado = UUID.fromString(uuid);
        try {
            Profissional prof = profissionalRepository.findProfissionalByUuid(uuidFormatado);
            prof.setSituacao(Profissional.Situacao.valueOf(novaSituacao));
            profissionalRepository.save(prof);
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }

    public boolean mudarAcesso(String uuid, boolean mudarAdm) {//melhorar logica pra quando nao encontrar profissional
        UUID uuidformatado = UUID.fromString(uuid);
        Profissional prof = profissionalRepository.findProfissionalByUuid(uuidformatado);
            prof.setAdministrador(mudarAdm);
            profissionalRepository.save(prof);
            return true;
    }


}
