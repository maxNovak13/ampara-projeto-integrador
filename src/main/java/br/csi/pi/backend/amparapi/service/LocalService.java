package br.csi.pi.backend.amparapi.service;

import br.csi.pi.backend.amparapi.model.local.DadosLocalDTO;
import br.csi.pi.backend.amparapi.model.local.Local;
import br.csi.pi.backend.amparapi.model.local.LocalRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LocalService {
    private final LocalRepository localRepository;

    public LocalService(LocalRepository localRepository) {
        this.localRepository = localRepository;
    }

    public void salvarLocal(Local local) {
        localRepository.save(local);
    }

    public List<Local> listarLocais() {
        return localRepository.findAll();
    }

    public List<DadosLocalDTO> buscarLocaisPorNome(String nome) {
        return localRepository.findLocalByNomeContaining(nome.toUpperCase()).stream().map(DadosLocalDTO::new).toList();
    }

}
