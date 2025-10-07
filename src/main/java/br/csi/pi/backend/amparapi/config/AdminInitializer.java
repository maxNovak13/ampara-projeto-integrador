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


    @Value("${ADMIN_EMAIL}")
    private String adminEmail;

    @Value("${ADMIN_PASSWORD}")
    private String adminPassword;

    public AdminInitializer(PasswordEncoder passwordEncoder, ProfissionalRepository profissionalRepository) {
        this.passwordEncoder = passwordEncoder;
        this.profissionalRepository = profissionalRepository;
    }

    @PostConstruct
    public void init() {
        if (adminEmail == null || adminEmail.isBlank() || adminPassword == null || adminPassword.isBlank()) {
            throw new IllegalStateException(
                    "As variáveis de ambiente ADMIN_EMAIL e ADMIN_PASSWORD devem estar definidas!"
            );
        }

        if (profissionalRepository.countAtivosAdmin() == 0) {
            Profissional admin = new Profissional();
            admin.setNome("Administrador");
            admin.setSituacao(Profissional.Situacao.ATIVO);
            admin.setProfissao("Administrador");
            admin.setRegistro("ADMIN");
            admin.setSenha(passwordEncoder.encode(adminPassword));
            admin.setEmail(adminEmail);
            admin.setRole(Profissional.Role.ADMIN);

            profissionalRepository.save(admin);
            System.out.println("Usuário administrador criado: " + adminEmail);
        } else {
            System.out.println("Usuário administrador já existe, não será recriado.");
        }
    }
}

