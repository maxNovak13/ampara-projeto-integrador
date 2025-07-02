package br.csi.pi.backend.amparapi.model.profissional;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ProfissionalRepository extends JpaRepository<Profissional, Long> {

    Optional<Profissional> findProfissionalByUuid(UUID uuid);

    Optional<Profissional> findByEmail(String email);

}
