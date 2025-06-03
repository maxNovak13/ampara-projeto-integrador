package br.csi.pi.backend.amparapi.model.local;

import org.springframework.data.jpa.repository.JpaRepository;

public interface LocalRepository extends JpaRepository<Local, Long> {
    public Local findLocalById(Long id);
    public void deleteLocalById(Long id);
}
