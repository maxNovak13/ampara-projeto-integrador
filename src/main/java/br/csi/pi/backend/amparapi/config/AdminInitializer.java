package br.csi.pi.backend.amparapi.config;


import br.csi.pi.backend.amparapi.model.profissional.Profissional;
import br.csi.pi.backend.amparapi.model.profissional.ProfissionalRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AdminInitializer {

    private final ProfissionalRepository profissionalRepository;

    private final PasswordEncoder passwordEncoder;


    @Value("${ADMIN_EMAIL:admin@ampara.com}")
    private String adminEmail;

    @Value("${ADMIN_PASSWORD:G7m!aR2q}")
    private String adminPassword;

    public AdminInitializer(PasswordEncoder passwordEncoder, ProfissionalRepository profissionalRepository) {
        this.passwordEncoder = passwordEncoder;
        this.profissionalRepository = profissionalRepository;
    }

    @PostConstruct
    public void init() {
        if (profissionalRepository.countAtivosAdmin() == 0) {
            Profissional admin = new Profissional();
            admin.setNome("Administrador");
            admin.setEmail("admin@ampara.com");
            admin.setSituacao(Profissional.Situacao.ATIVO);
            admin.setProfissao("Administrador");
            admin.setRegistro("ADMIN");
            admin.setSenha(passwordEncoder.encode(adminPassword));
            admin.setRole(Profissional.Role.valueOf("ADMIN"));

            profissionalRepository.save(admin);
            System.out.println("Usuário administrador padrão criado: " + adminEmail);
        }
        else {
            System.out.println("Usuário administrador já existe, não será recriado.");
        }
    }
}

