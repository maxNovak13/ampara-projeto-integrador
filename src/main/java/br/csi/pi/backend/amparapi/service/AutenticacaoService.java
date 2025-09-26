package br.csi.pi.backend.amparapi.service;

import br.csi.pi.backend.amparapi.model.profissional.ProfissionalRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AutenticacaoService implements UserDetailsService  {
    private final ProfissionalRepository profissionalRepository;

    public AutenticacaoService(ProfissionalRepository profissionalRepository, PasswordEncoder passwordEncoder) {
        this.profissionalRepository = profissionalRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return profissionalRepository.findByEmail(email)
                .orElseThrow(() -> {
                    System.out.println("[AUTH] Email não encontrado: " + email);
                    return new UsernameNotFoundException("Email ou senha incorretos");
                });
    }
}
