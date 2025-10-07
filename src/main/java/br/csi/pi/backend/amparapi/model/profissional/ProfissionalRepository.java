package br.csi.pi.backend.amparapi.model.profissional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface ProfissionalRepository extends JpaRepository<Profissional, Long> {

    Optional<Profissional> findProfissionalByUuid(UUID uuid);

    Optional<Profissional> findByEmail(String email);


    @Query("SELECT COUNT(p) FROM Profissional p " +
            "WHERE p.situacao = 'ATIVO' AND p.role = 'ADMIN'")
    long countAtivosAdmin();
}
