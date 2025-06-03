package br.csi.pi.backend.amparapi.service;

import br.csi.pi.backend.amparapi.model.mulher.DadosMulherDTO;
import br.csi.pi.backend.amparapi.model.mulher.Mulher;
import br.csi.pi.backend.amparapi.model.mulher.MulherRepository;
import br.csi.pi.backend.amparapi.model.mulher.MulherResultadoBuscaDTO;
import java.time.format.DateTimeFormatter;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
public class MulherService {
    private final MulherRepository mulherRepository;

    public MulherService(MulherRepository mulherRepository) {
        this.mulherRepository = mulherRepository;
    }

    public void cadastraMulher(Mulher mulher) {
        mulherRepository.save(mulher);
    }

    public Mulher buscarPorUuid(String uuid) {
        return mulherRepository.findMulherByUuid(UUID.fromString(uuid));
    }

    public Mulher atualizarDadosMulher(Mulher mulher, String uuid) {
        Mulher mDadosAnteriores = buscarPorUuid(uuid);
        Mulher m = this.mulherRepository.getReferenceById(mDadosAnteriores.getId());
        m.setNome(mulher.getNome());
        m.setDataNascimento(mulher.getDataNascimento());
        m.setCpf(mulher.getCpf());
        m.setTelefone(mulher.getTelefone());
        m.setNomeMae(mulher.getNomeMae());
        m.setEscolaridade(mulher.getEscolaridade());
        m.setProfissao(mulher.getProfissao());
        m.setEstadoCivil(mulher.getEstadoCivil());
        m.setEndereco(mulher.getEndereco());
        return this.mulherRepository.save(m);
    }

    public List<DadosMulherDTO> listarMulheres() {//apenas para teste
        return mulherRepository.findAll().stream().map(DadosMulherDTO::new).toList();
    }
    //BUSCAS

    public List<MulherResultadoBuscaDTO> buscarPorFiltro(String filtro, String valor) {
        if (valor == null || valor.isBlank()) {
            return Collections.emptyList();
        }
        switch (filtro) {
            case "nome":
                return mulherRepository.findMulherByNomeContaining(valor.toUpperCase())
                        .stream().map(MulherResultadoBuscaDTO::new).toList();
            case "mae":
                return mulherRepository.findMulherByNomeMaeContaining(valor.toUpperCase())
                        .stream().map(MulherResultadoBuscaDTO::new).toList();
            case "cpf":
                return mulherRepository.findMulherByCpf(valor.replaceAll("\\D", ""))
                        .stream().map(MulherResultadoBuscaDTO::new).toList();
            case "data":
                // Aceita valor em dd-MM-yyyy ou dd/MM/yyyy
                if (valor.contains("/")) {
                    valor = valor.replace("/", "-");
                }
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                LocalDate data = LocalDate.parse(valor, formatter);
                System.out.println("VALOR RECEBIDO" + valor + "DATA TRATADA" + data);
                return mulherRepository.findMulherByDataNascimento(data)
                        .stream().map(MulherResultadoBuscaDTO::new).toList();
            default:
                throw new IllegalArgumentException("Filtro inválido: " + filtro);
        }
    }

}
