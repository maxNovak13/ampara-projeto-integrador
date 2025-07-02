package br.csi.pi.backend.amparapi.service;

import br.csi.pi.backend.amparapi.model.agressor.Agressor;
import br.csi.pi.backend.amparapi.model.agressor.AgressorRepository;
import br.csi.pi.backend.amparapi.model.local.LocalRepository;
import br.csi.pi.backend.amparapi.model.mulher.Mulher;
import br.csi.pi.backend.amparapi.model.mulher.MulherRepository;
import br.csi.pi.backend.amparapi.model.profissional.ProfissionalRepository;
import br.csi.pi.backend.amparapi.model.registro.DadosRegistroTabelaDTO;
import br.csi.pi.backend.amparapi.model.registro.Registro;
import br.csi.pi.backend.amparapi.model.registro.RegistroRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class RegistroService {
    private final RegistroRepository registroRepository;
    private final MulherRepository mulherRepository;
    private final LocalRepository localRepository;
    private final ProfissionalRepository profissionalRepository;
    private final AgressorRepository agressorRepository;


    public RegistroService(RegistroRepository registroRepository,
                           MulherRepository mulherRepository,
                           LocalRepository localRepository,
                           ProfissionalRepository profissionalRepository,
                           AgressorRepository agressorRepository) {
        this.registroRepository = registroRepository;
        this.mulherRepository = mulherRepository;
        this.localRepository = localRepository;
        this.profissionalRepository = profissionalRepository;
        this.agressorRepository = agressorRepository;
    }

    public Registro salvarRegistro(Registro registro) {//alterar talvez para receber uuid de mulher/profissional
        // Busca as entidades existentes do banco
        var mulherUuid = mulherRepository.findMulherByUuid(registro.getMulher().getUuid());
        var mulherId = mulherRepository.findById(mulherUuid.getId())
                .orElseThrow(() -> new IllegalArgumentException("Mulher não encontrada"));

        var local = localRepository.findById(registro.getLocal().getId())
                .orElseThrow(() -> new IllegalArgumentException("Local não encontrado"));

        var profissional = profissionalRepository.findProfissionalByUuid(registro.getProfissional().getUuid())
                .orElseThrow(() -> new IllegalArgumentException("Profissional não encontrado"));

        // Associa entidades gerenciadas ao registro
        registro.setMulher(mulherId);
        registro.setLocal(local);
        registro.setProfissional(profissional);

        // Ajusta o valor do código referente a mulher e o número de registros
        Integer ultimoCodigo = registroRepository.findMaxCodigoByMulherId(registro.getMulher().getId());
        registro.setCodigo(ultimoCodigo != null ? ultimoCodigo + 1 : 1);


        // Verifica e associa cada agressor (se vierem no objeto)
        if (registro.getAgressores() != null && !registro.getAgressores().isEmpty()) {
            for (Agressor agressor : registro.getAgressores()) {
                agressor.setRegistro(registro); // associa o registro no agressor
            }
        }

        return registroRepository.save(registro);
    }

    public Registro buscarPorUuid(String uuid) {
        return registroRepository.findRegistroByUuid(UUID.fromString(uuid));
    }


    public List<DadosRegistroTabelaDTO> listarPorMulherDesc(String uuid) {
        Mulher mulherBuscada = mulherRepository.findMulherByUuid(UUID.fromString(uuid));
        return registroRepository.findByMulherIdOrderByCreatedAtDesc(mulherBuscada.getId()).
                stream().map(DadosRegistroTabelaDTO::new).toList();
    }
}
