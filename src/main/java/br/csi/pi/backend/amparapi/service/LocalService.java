package br.csi.pi.backend.amparapi.service;

import br.csi.pi.backend.amparapi.model.local.Local;
import br.csi.pi.backend.amparapi.model.local.LocalRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    public void excluirLocal(Long id) {
        this.localRepository.deleteLocalById(id);
    }
}
