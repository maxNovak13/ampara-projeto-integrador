package br.csi.pi.backend.amparapi.model.mulher;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface MulherRepository extends JpaRepository<Mulher, Long> {
    public Mulher findMulherByUuid(UUID uuid);

    public List<Mulher> findMulherByNomeContaining(String nome);

    public List<Mulher> findMulherByNomeMaeContaining(String nomeMae);

    public List<Mulher> findMulherByDataNascimento(LocalDate dataNascimento);

    public List<Mulher> findMulherByCpf(String cpf);
}
