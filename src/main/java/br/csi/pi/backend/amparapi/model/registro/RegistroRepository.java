package br.csi.pi.backend.amparapi.model.registro;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RegistroRepository  extends JpaRepository<Registro, Long> {
    public Registro findRegistroByUuid(UUID uuid);

    public List<Registro> findByMulherIdOrderByCreatedAtDesc(Long id);

    @Query("SELECT MAX(r.codigo) FROM Registro r WHERE r.mulher.id = :mulherId")//auxilia para padronizar codigo de registro
    Integer findMaxCodigoByMulherId(@Param("mulherId") Long mulherId);
}
