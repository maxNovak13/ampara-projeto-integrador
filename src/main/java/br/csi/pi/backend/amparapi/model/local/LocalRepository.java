package br.csi.pi.backend.amparapi.model.local;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LocalRepository extends JpaRepository<Local, Long> {
    List<Local> findLocalByNomeContaining(String nome);

}
