package br.csi.pi.backend.amparapi.service;

import br.csi.pi.backend.amparapi.model.agressor.Agressor;
import br.csi.pi.backend.amparapi.model.agressor.AgressorRepository;
import br.csi.pi.backend.amparapi.model.registro.Registro;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class AgressorService {
    private final AgressorRepository agressorRepository;

    public AgressorService(AgressorRepository agressorRepository) {
        this.agressorRepository = agressorRepository;
    }

    public Agressor salvarAgressor(Agressor agressor){
        return agressorRepository.save(agressor);
    }

    public Agressor buscarPorUuid(String uuid) {
        return agressorRepository.findAgressorByUuid(UUID.fromString(uuid));
    }

}
