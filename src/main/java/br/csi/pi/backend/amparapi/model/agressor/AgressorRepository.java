package br.csi.pi.backend.amparapi.model.agressor;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface AgressorRepository extends JpaRepository<Agressor, Long> {

    Agressor findAgressorByUuid(UUID uuid);

    public List<Agressor> findByRegistroId(Long id);
}
